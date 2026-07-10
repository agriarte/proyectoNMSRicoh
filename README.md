# Proyecto NMS Ricoh

## Descripción

Proyecto inicial desarrollado con Spring Boot para la monitorización de impresoras Ricoh mediante SNMP en redes locales de oficina.

Esta es una versión temprana del sistema cuyo objetivo es descubrir dispositivos SNMP, obtener información básica de los equipos e identificar impresoras Ricoh.

---

# Interfaz Web

La aplicación incorpora una interfaz web sencilla para realizar el descubrimiento de dispositivos desde el navegador.

URL:

http://localhost:8080/

Características:

- Introducir la subred a escanear (ejemplo: `192.168.1`)
- Descubrimiento automático de dispositivos SNMP activos
- Consola de eventos en tiempo real
- Barra de progreso durante el escaneo
- Tabla con los dispositivos encontrados y su descripción (`sysDescr`)

Actualmente la interfaz está orientada a pruebas y validación del motor de descubrimiento.

---

# Descubrimiento de dispositivos

## Endpoint REST

```
GET http://localhost:8080/discovery?subnet=192.168.1
```

Realiza un escaneo completo del rango indicado buscando dispositivos con SNMP activo.

Para cada dispositivo encontrado se consulta el OID estándar:

- `1.3.6.1.2.1.1.1.0` → **sysDescr**

Con esta información es posible comenzar a clasificar los dispositivos encontrados (PCs, impresoras, switches, etc.).

---

# Descubrimiento en tiempo real (Server-Sent Events)

También existe un endpoint que devuelve el progreso del descubrimiento en tiempo real mediante **Server-Sent Events (SSE)**.

```
GET http://localhost:8080/discovery/stream?subnet=192.168.1
```

Durante el escaneo el servidor va enviando eventos conforme se procesan las direcciones IP.

Ejemplo de salida:

```text
data:{"time":"23:12:23.7195456","ip":"192.168.1.201","event":"PROBING"}

data:{"time":"23:12:23.7210367","ip":"192.168.1.197","event":"PROBING"}

data:{"time":"23:12:23.7265412","ip":"192.168.1.134","event":"FOUND"}

data:{"time":"23:12:23.7348910","ip":"192.168.1.134","event":"DESCRIPTION","description":"Windows Version 10"}

data:{"event":"FINISHED"}
```

Eventos disponibles:

- **PROBING** → Se está comprobando una dirección IP.
- **FOUND** → El dispositivo responde mediante SNMP.
- **DESCRIPTION** → Se ha obtenido el `sysDescr`.
- **FINISHED** → El escaneo ha finalizado.

Este endpoint es el utilizado por la interfaz web para mostrar el progreso del descubrimiento en tiempo real.

---

# Pruebas con dispositivos Windows (SNMP)

Para probar con dispositivos Windows con SNMP habilitado se pueden obtener datos básicos mediante los OIDs estándar.

## OIDs utilizados

- SysDescr: `1.3.6.1.2.1.1.1.0`
- SysName: `1.3.6.1.2.1.1.5.0`

## Endpoint

```
http://localhost:8080/devices/win/192.168.1.x
```

Sustituir `192.168.1.x` por la IP del equipo Windows.

---

# Pruebas con impresoras Ricoh

Para las impresoras Ricoh se utilizan OIDs propietarios del fabricante.

## OIDs utilizados

- Model: `1.3.6.1.4.1.367.3.2.1.1.1.1.0`
- Serial: `1.3.6.1.4.1.367.3.2.1.2.1.4.0`
- Firmware: `1.3.6.1.4.1.367.3.2.1.1.1.2.0`
- Device Status: `1.3.6.1.4.1.367.3.2.1.2.2.13.0`
- Total Counter: `1.3.6.1.4.1.367.3.2.1.2.19.1.0`
- Toner Base: `1.3.6.1.4.1.367.3.2.1.2.24.1.1.5.*`

## Endpoint

```
http://localhost:8080/devices/ricoh/192.168.1.x
```

Sustituir `192.168.1.x` por la IP de la impresora Ricoh.

---

# Estado del proyecto

- ✔ Descubrimiento de dispositivos mediante SNMP
- ✔ Interfaz web para realizar escaneos
- ✔ Consola de eventos en tiempo real
- ✔ Endpoint SSE para seguimiento del escaneo
- ✔ Lectura de información básica (`sysDescr`)
- ✔ Lectura de información específica de impresoras Ricoh
- 🚧 Clasificación automática de dispositivos
- 🚧 Inventario persistente
- 🚧 Monitorización continua

---

# Notas

- Proyecto en fase inicial.
- Pensado para redes locales de oficina.
- Requiere SNMP habilitado en los dispositivos destino.