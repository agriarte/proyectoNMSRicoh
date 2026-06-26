# Proyecto NMS Ricoh

## Descripción
Proyecto inicial en Spring Boot para la monitorización de impresoras Ricoh mediante SNMP en redes locales de oficina.

Versión temprana del sistema.

---

## Pruebas con dispositivos Windows (SNMP)

Para probar con dispositivos Windows con SNMP habilitado, se pueden obtener datos básicos como:

### OIDs utilizados
- SysDescr: `1.3.6.1.2.1.1.1.0`
- SysName: `1.3.6.1.2.1.1.5.0`

### Endpoint de prueba

http://localhost:8080/devices/win/192.168.1.x

> Sustituir `192.168.1.x` por la IP del equipo Windows

---

## Pruebas con impresoras Ricoh

Para impresoras Ricoh se consultan OIDs propietarios del fabricante.

### OIDs Ricoh utilizados

- Model: `1.3.6.1.4.1.367.3.2.1.1.1.1.0`
- Serial: `1.3.6.1.4.1.367.3.2.1.2.1.4.0`
- Firmware: `1.3.6.1.4.1.367.3.2.1.1.1.2.0`
- Device Status: `1.3.6.1.4.1.367.3.2.1.2.2.13.0`
- Total Counter: `1.3.6.1.4.1.367.3.2.1.2.19.1.0`
- Toner Base: `1.3.6.1.4.1.367.3.2.1.2.24.1.1.5.`

### Endpoint de prueba

http://localhost:8080/devices/ricoh/192.168.1.x

> Sustituir `192.168.1.x` por la IP de la impresora Ricoh

---

## Notas
- Proyecto en fase inicial
- Pensado para redes locales de oficina
- Requiere SNMP habilitado en dispositivos destino