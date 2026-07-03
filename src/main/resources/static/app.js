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

    table.innerHTML = "";
    consoleDiv.innerHTML = "";

    status.className =
        "alert alert-info";

    status.innerHTML =
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

            const data =
                JSON.parse(
                    event.data);

            if(data.event ===
                    "START") {

                consoleDiv.innerHTML +=
                    "=== ESCANEO INICIADO ===<br>";
            }

            if(data.event ===
                    "PROBING") {

                consoleDiv.innerHTML +=

                    "🔍 "
                    + data.time
                    + " "
                    + data.ip
                    + "<br>";

                consoleDiv.scrollTop =
                    consoleDiv.scrollHeight;
            }

            if(data.event ===
                    "FOUND") {

                consoleDiv.innerHTML +=

                    "🟢 "
                    + data.ip
                    + "<br>";

                const row =
                    table.insertRow();

                row.insertCell(0)
                    .innerHTML =
                    data.ip;

                row.insertCell(1)
                    .innerHTML =
                    data.sysDescr;

                consoleDiv.scrollTop =
                    consoleDiv.scrollHeight;
            }

            if(data.event ===
                    "COMPLETE") {

                consoleDiv.innerHTML +=
                    "<br>=== ESCANEO FINALIZADO ===";

                status.className =
                    "alert alert-success";

                status.innerHTML =
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

            status.innerHTML =
                "Error";

            source.close();
        };
}