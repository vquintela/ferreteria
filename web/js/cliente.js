/* global fetch */

function openEdicion(evt, edicion) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(edicion).style.display = "block";
    evt.currentTarget.className += " active";
}

class Cliente {
    static inicializar() {
        let elemInsertar = document.querySelector('#cliente_agregar');
        elemInsertar.setAttribute('onclick', "Cliente.insertar();");
        let elemConsultar = document.querySelector('#cliente_ver');
        elemConsultar.setAttribute('onclick', "Cliente.consultar();");
    }

    static insertar() {
        let cliente = {};
        cliente.nombre = document.getElementById("nombre").value;
        cliente.apellido = document.getElementById("apellido").value;
        cliente.dni = document.getElementById("dni").value;
        cliente.telefono = document.getElementById("telefono").value;
        cliente.direccion = document.getElementById("direccion").value;
        cliente.mail = document.getElementById("mail").value;
        
        let personaJSON = JSON.stringify(cliente);

        fetch("ClienteServlet",
                {method: 'POST', body: personaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Cliente.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static modalEd(texto) {
        let mascara = document.getElementById('lamascara');
       
        mascara.style.display = "block";
        document.querySelector('#panelResultados').innerHTML = texto;
    }
    
    static consultar(){
        fetch("ClienteServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        document.querySelector("#listado").innerHTML =
                                    "<br>\n\
                                    <tr>\n\
                                    <th class='text-center'>ID</th>\n\
                                    <th class='text-center'>Nombre</th>\n\
                                    <th class='text-center'>Apellido</th>\n\
                                    <th class='text-center'>Acciones</th>\n\
                                    </tr>";
                        let clientes = JSON.parse(datotexto);
                        for (let i = 0; i < clientes.length; i++) {
                            document.querySelector("#listado").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ clientes[i].id +"</td>\n\
                                    <td class='text-center'>"+ clientes[i].nombre +"</td>\n\
                                    <td class='text-center'>"+ clientes[i].apellido +"</td>\n\
                                    <td class='text-center'>\n\
                                    <button type='button' class='btn btn-warning btn-sm' onclick='Cliente.editar(" + clientes[i].id + ");'>Editar</button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Cliente.eliminar(" + clientes[i].id + ");'>\n\
                                    Eliminar\n\
                                    </button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Cliente.verEmpleado(" + clientes[i].id + ");'>\n\
                                    Ver\n\
                                    </button>\n\
                                    </td></tr>";
                        }   
                    }catch(Excepcion){
                        Empleado.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static eliminar(paramId){
        fetch("ClienteServlet?&q=" + paramId,
                {method: 'DELETE'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    Cliente.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static verEmpleado(empleado){
        fetch("ClienteServlet?&q=" + empleado,
                {method: 'GET'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    let cliente = JSON.parse(datotexto);
                    document.querySelector("#listado").innerHTML =
                            "<br><h5 class='modal-title'>Informacion del Empleado</h5>\n\
                            <br>\n\
                            <div class='list-group'>\n\
                                <div class='list-group'>\n\
                                    <li class='list-group-item'><b>Nombre: </b>" + cliente.nombre + "</li>\n\
                                    <li class='list-group-item'><b>Apellido: </b>" + cliente.apellido + "</li>\n\
                                    <li class='list-group-item'><b>DNI: </b>" + cliente.dni + "</li>\n\
                                    <li class='list-group-item'><b>Telefono: </b>" + cliente.telefono + "</li>\n\
                                    <li class='list-group-item'><b>Direccion: </b>" + cliente.direccion + "</li>\n\
                                    <li class='list-group-item'><b>Mail: </b>" + cliente.mail + "</li>\n\
                                </div>\n\
                            </div>";
                    });
    }
    
    static editar(id){
        fetch("ClienteServlet?&q=" + id,
                {method: 'GET'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    let cliente = JSON.parse(datotexto);
                    document.querySelector("#listado").innerHTML =
                            "<br><h5 class='modal-title'>Informacion del Empleado</h5>\n\
                            <br>\n\
                            <div class='list-group'>\n\
                                <div class='list-group'>\n\
                                    <li class='list-group-item' id='id2' value="+cliente.id +"><b>ID: </b>" + cliente.id + "</li>\n\
                                    <li class='list-group-item'><b>Nombre: </b><input type='text' id='nombre2' value=" + cliente.nombre + "></li>\n\
                                    <li class='list-group-item'><b>Apellido: </b><input id='apellido2' value=" + cliente.apellido + "></li>\n\
                                    <li class='list-group-item'><b>DNI: </b><input id='dni2' value=" + cliente.dni + "></li>\n\
                                    <li class='list-group-item'><b>Telefono: </b><input id='telefono2' value=" + cliente.telefono + "></li>\n\
                                    <li class='list-group-item'><b>Direccion: </b><input id='direccion2' value=" + cliente.direccion + "></li>\n\
                                    <li class='list-group-item'><b>Mail: </b><input id='mail2' value=" + cliente.mail + "></li>\n\
                                </div>\n\
                            </div>\n\
                                <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Cliente.editado();'>\n\
                                    Editar\n\
                                </button>";
                    });
    }
    
    static editado(){
        let cliente = {};
        cliente.id = document.getElementById("id2").value;
        cliente.nombre = document.getElementById("nombre2").value;
        cliente.apellido = document.getElementById("apellido2").value;
        cliente.dni = document.getElementById("dni2").value;
        cliente.telefono = document.getElementById("telefono2").value;
        cliente.direccion = document.getElementById("direccion2").value;
        cliente.mail = document.getElementById("mail2").value;
        
        let personaJSON = JSON.stringify(cliente);

        fetch("ClienteServlet",
                {method: 'PUT', body: personaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Cliente.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
}


Cliente.inicializar();

