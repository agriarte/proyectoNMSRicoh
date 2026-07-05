function log(consoleDiv, text) {

    const line =
        document.createElement(
            "div");

    line.textContent =
        text;

    consoleDiv.appendChild(
        line);

    consoleDiv.scrollTop =
        consoleDiv.scrollHeight;
}


function scan() {

    const table =
        document.getElementById(
            "devices");

    const consoleDiv =
        document.getElementById(
            "console");

    const status =
        document.getElementById(
            "status");

    const progress =
        document.getElementById(
            "progress");

    // Limpiar resultados anteriores
    table.replaceChildren();
    consoleDiv.replaceChildren();

    status.className =
        "alert alert-info";

    status.textContent =
        "Escaneando red...";

    progress.style.width =
        "100%";

    const subnet =
        document.getElementById(
            "subnet").value;

    const source =
        new EventSource(
            "/discovery/stream?subnet="
            + subnet);

    source.onmessage =
        function(event) {

            // Consola de depuración
            console.log(
                performance.now(),
                event.data);

            const data =
                JSON.parse(
                    event.data);

            // Inicio
            if (data.event ===
                    "START") {

                log(
                    consoleDiv,
                    "=== ESCANEO INICIADO ===");
            }

            // Sondeo
            if (data.event ===
                    "PROBING") {

                log(
                    consoleDiv,
                    "🔍 "
                    + data.time
                    + " "
                    + data.ip);
            }

            // Dispositivo encontrado
            if (data.event ===
                    "FOUND") {

                log(
                    consoleDiv,
                    "🟢 "
                    + data.ip);

                const row =
                    table.insertRow();

                row.insertCell(0)
                   .textContent =
                   data.ip;

                row.insertCell(1)
                   .textContent =
                   data.sysDescr;
            }

            // Fin del escaneo
            if (data.event ===
                    "COMPLETE") {

                log(
                    consoleDiv,
                    "");

                log(
                    consoleDiv,
                    "=== ESCANEO FINALIZADO ===");

                status.className =
                    "alert alert-success";

                status.textContent =
                    "Escaneo completado";

                progress.style.width =
                    "0%";

                source.close();
            }
        };

    source.onerror =
        function() {

            progress.style.width =
                "0%";

            status.className =
                "alert alert-danger";

            status.textContent =
                "Error";

            source.close();
        };
}