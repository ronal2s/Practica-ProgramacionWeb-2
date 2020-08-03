
<!DOCTYPE html>
<html lang="en" manifest="/templates/sinconexion.appcache">
<head>
    <meta charset="UTF-8">
    <script src="/templates/js/offline.min.js"></script>
    <link href="/templates/css/bootstrap.css" rel="stylesheet">
    <link href="/templates/css/globalStyles.css" rel="stylesheet">
    <link rel="stylesheet" href="/templates/css/offline-theme-chrome.css" />
    <link rel="stylesheet" href="/templates/css/offline-language-spanish.css" />
    <link rel="stylesheet" href="/templates/css/offline-language-spanish-indicator.css" />

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script type="text/javascript" src="/templates/js/bootstrap.js"></script>
    <script type="text/javascript" src="/templates/js/jquery-3.5.1.slim.min.js"></script>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDmO0JHOHAXY2C3Ud49KbMSwFf3APep1Ow&callback=initMap&libraries=&v=weekly"
            defer
    ></script>
    <script>
        var indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB || window.moz_indexedDB

        var dataBase = indexedDB.open("parcial2", 1);

        dataBase.onupgradeneeded = function (e) {
            active = dataBase.result;
            var formularios = active.createObjectStore("formularios", { keyPath : 'id', autoIncrement : true });

            formularios.createIndex('por_id', 'id', {unique: true});

            var usuario = active.createObjectStore("usuario", { keyPath : 'user', autoIncrement : false });
            usuario.createIndex('por_user', 'user', {unique : true});
        };

        dataBase.onsuccess = function (e) {
            console.log(e);
        };

        dataBase.onerror = function (e) {
            console.error(e.target.errorCode);
        };


        function addItem() {
            var dbActiva = dataBase.result; 
            var transaccion = dbActiva.transaction(["formularios"], "readwrite");

            transaccion.onerror = () => {
                alert(request.error.name + '\n\n' + request.error.message);
            };

            transaccion.oncomplete = () => {
                document.querySelector("#nombre").value = '';
                alert("Registrado")
            };

            var formularios = transaccion.objectStore("formularios");

            let temp = {
                nombre: document.querySelector("#nombre").value,
                sector: document.querySelector("#sector").value,
                nivelEscolar: document.querySelector("#nivelEscolar").value,
                latitud: document.querySelector("#latitud").value,
                longitud: document.querySelector("#longitud").value
            }

            var request = formularios.put(temp);

            request.onerror = function (e) {
                var mensaje = "Error: "+e.target.errorCode;
                console.error(mensaje);
                alert(mensaje)
            };

            request.onsuccess = () => {
                console.log("Datos Procesado con exito");
                document.querySelector("#nombre").value = "";
                document.querySelector("#sector").value = "";
                document.querySelector("#nivelEscolar").value = "";
                document.querySelector("#latitud").value = "";
                document.querySelector("#longitud").value = "";
            };
        }

        function updateItem() {

            let identificacion = prompt("ID");
            let nombre = prompt("Nombre");
            let sector = prompt("Sector");
            let nivel = prompt("Escolar");

            const transaccion = dataBase.result.transaction(["formularios"],"readwrite");
            const formularios = transaccion.objectStore("formularios");
            const requestEdicion = formularios.get(identificacion);


            requestEdicion.onsuccess = () => {

                let resultado = requestEdicion.result;
                console.log(JSON.stringify(resultado));

                if(resultado !== undefined){

                    resultado.nombre = nombre;
                    resultado.sector = sector;
                    resultado.nivelEscolar = nivel;

                    let solicitudUpdate = formularios.put(resultado);

                    solicitudUpdate.onsuccess = () => {
                        
                    }
                    solicitudUpdate.onerror = () => {
                        
                    }
                }else{
                    console.log("Ha ocurrido un error");
                }
            };
        }


        function getItems() {
            
            var data = dataBase.result.transaction(["formularios"]);
            var formularios = data.objectStore("formularios");
            var contador = 0;
            var totalItems=[];
            
            formularios.openCursor().onsuccess=function(e) {

                var cursor = e.target.result;
                if(cursor){
                    contador++;
                    totalItems.push(cursor.value);                    
                    cursor.continue();

                }else {
                    console.log(totalItems.length);
                }
            };

            data.oncomplete = function () {
                printDataValues(totalItems);
            }
            console.log(nuevo);
        }

        function printDataValues(itemsTable) {
            var tabla = document.createElement("table");
            tabla.setAttribute('class', 'table table-bordered')
            var filaTabla = tabla.insertRow();
            filaTabla.insertCell().textContent = "ID";
            filaTabla.insertCell().textContent = "Nombre";
            filaTabla.insertCell().textContent = "Sector";
            filaTabla.insertCell().textContent = "Nivel Escolar";
            filaTabla.insertCell().textContent = "Latitud";
            filaTabla.insertCell().textContent = "Longitud";

            for (var key in itemsTable) {
                filaTabla = tabla.insertRow();
                filaTabla.insertCell().textContent = ""+itemsTable[key].id;
                filaTabla.insertCell().textContent = ""+itemsTable[key].nombre;
                filaTabla.insertCell().textContent = ""+itemsTable[key].sector;
                filaTabla.insertCell().textContent = ""+itemsTable[key].nivelEscolar;
                filaTabla.insertCell().textContent = ""+itemsTable[key].latitud;
                filaTabla.insertCell().textContent = ""+itemsTable[key].longitud;
            }

            document.getElementById("listaFormularios").innerHTML="";
            document.getElementById("listaFormularios").appendChild(tabla);
        }

        function deleteItem() {

            var id = prompt("ID");

            var data = dataBase.result.transaction(["formularios"], "readwrite");
            var formularios = data.objectStore("formularios");

            formularios.delete(id).onsuccess = function (e) {
                console.log("Formulario eliminado...");
            };
        }


        var webSocket;
        var tiempoReconectar = 5000;

        $(document).ready(function(){
            conectar();

            $("#boton").click(function(){

                if(!webSocket || webSocket.readyState == 3) {

                    alert("Una conexión a internet es necesaria")

                }else{
                    let data = dataBase.result.transaction(["formularios"]);
                    let formularios = data.objectStore("formularios");

                    formularios.openCursor().onsuccess = function (e) {
                        var cursor = e.target.result;
                        if (cursor) {
                            webSocket.send(JSON.stringify(cursor.value));
                            cursor.continue();
                        } 
                    };
                }
            });
        });

        function recibirInformacionServidor(mensaje){
            $("#mensajeServidor").append(mensaje.data);
        }

        function conectar() {
            webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor");
            var req = new XMLHttpRequest();
            req.timeout = 5000;
            req.open('GET', "http://" + location.hostname + ":" + location.port + "/formulario", true);
            req.send();


            webSocket.onmessage = function(data){recibirInformacionServidor(data);};
            webSocket.onopen  = function(e){
                var req = new XMLHttpRequest();
                req.timeout = 5000;
                req.open('GET', "http://" + location.hostname + ":" + location.port + "/formulario", true);
                req.send();
                };
            webSocket.onclose = function(e){

                console.log("Desconectado - status "+this.readyState);
                var req = new XMLHttpRequest();
                req.timeout = 5000;
                req.open('GET', "http://" + location.hostname + ":" + location.port + "/formulario", true);
                req.send();
            };
        }

        function verificarConexion(){
            if(!webSocket || webSocket.readyState == 3){
                conectar();
            }
        }

        setInterval(verificarConexion, tiempoReconectar); //para reconectar.
    </script>
    <style>
body, .body {
      background-color: #202020;
}

h1, label, td {
    color: white;
}
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">        
    </div>
</nav>
<main type="main">
    <div class="container">
    <div style="display: grid; justify-content: center; align-items: center; flex-direction: column;" >
        <br><h1 class="text-center">${title}</h1><br>
        <div class="container">
        <br><button id="boton" type="button" class="btn btn-light">Sincronizar Datos</button>
        </div>
    </div>

        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input class="form-control" type="text" id="nombre" name="nombre">
        </div>
    <div class="row">
    <div class="col">
      <div class="form-group">
            <label for="sector">Sector:</label>
            <input class="form-control" type="text" id="sector" name="sector">
        </div>
    </div>
    <div class="col">
      <div class="form-group">
            <label for="nivelEscolar">Nivel Escolar:</label>
            <select class="form-control" name="nivelEscolar" id="nivelEscolar">
                <#list choices as choice>
                    <option value="${choice}">${choice}</option>
                </#list>
            </select>
        </div>
    </div>
  </div>
        <#--  <div class="form-group">
            <label for="sector">Sector:</label>
            <input class="form-control" type="text" id="sector" name="sector">
        </div>  -->
        <#--  <div class="form-group">
            <label for="nivelEscolar">Nivel Escolar:</label>
            <select class="form-control" name="nivelEscolar" id="nivelEscolar">
                <#list choices as choice>
                    <option value="${choice}">${choice}</option>
                </#list>
            </select>
        </div>  -->

        <br><h1 class="text-center">Datos de GPS</h1><br>
    <div class="row">
    <div class="col">
      <div class="form-group"  >
            <label for="latitud">Latitud:</label>
            <input class="form-control" type="text" id="latitud" name="latitud" readonly>
        </div>
    </div>
    <div class="col">
      <div class="form-group" >
            <label for="longitud">Longitud:</label>
            <input class="form-control" type="text" id="longitud" name="longitud" readonly>
        </div>
    </div>
  </div>
        
        
        <button class="btn btn-secondary" onclick="getItems()">Listado</button>
        <button class="btn btn-secondary" onclick="updateItem()">Modificar</button>
        <button class="btn btn-secondary" onclick="deleteItem()">Eliminar un Formulario</button>
        <button class="btn btn-primary" onclick="addItem()">Guardar</button>
    </div>
    <br><br>
    <div id="listaFormularios"></div>

    <script type="text/javascript" src="/templates/js/jquery-3.5.1.slim.min.js"></script>
    <script>
        var id, cantidad = 0;
        var opcionesGPS = {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 0
        }

        $(document).ready(function(){

            navigator.geolocation.getCurrentPosition(function(geodata){
                var coordenadas = geodata.coords;
                document.querySelector("#latitud").value = coordenadas.latitude;
                document.querySelector("#longitud").value = coordenadas.longitude;
            }, function(){
                document.querySelector("#latitud").value = "ERROR GPS";
                document.querySelector("#longitud").value = "ERROR GPS";
            }, opcionesGPS);

            id = navigator.geolocation.watchPosition(function(geodata){
                var coordenadas = geodata.coords;
                document.querySelector("#latitud").value = coordenadas.latitude;
                document.querySelector("#longitud").value = coordenadas.longitude;
                cantidad++;
                if(cantidad>=5){
                    navigator.geolocation.clearWatch(id);
                }
            },function(error){
                document.querySelector("#latitud").value = "ERROR GPS. Codigo: "+error.code+", mensaje: "+error.message;
                document.querySelector("#longitud").value = "ERROR GPS. Codigo: "+error.code+", mensaje: "+error.message;
            });
        });
    </script>
</main>
<script type="text/javascript" src="/templates/js/jquery-3.5.1.slim.min.js"></script>
<script type="text/javascript" src="/templates/js/popper.min.js"></script>
<script type="text/javascript" src="/templates/js/bootstrap.js"></script>
</body>
</html>
