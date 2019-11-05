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

class Empleado {
    static inicializar() {
        let elemInsertar = document.querySelector('#empleado_agregar');
        elemInsertar.setAttribute('onclick', "Empleado.insertar();");
        let elemConsultar = document.querySelector('#empleado_ver');
        elemConsultar.setAttribute('onclick', "Empleado.consultar();");
    }

    static insertar() {
        let empleado = {};
        empleado.nombre = document.getElementById("nombre").value;
        empleado.apellido = document.getElementById("apellido").value;
        empleado.dni = document.getElementById("dni").value;
        empleado.telefono = document.getElementById("telefono").value;
        empleado.usuario = document.getElementById("usuario").value;
        empleado.password = document.getElementById("password").value;
        empleado.direccion = document.getElementById("direccion").value;
        empleado.mail = document.getElementById("mail").value;
        empleado.salario = document.getElementById("salario").value;
        
        let personaJSON = JSON.stringify(empleado);

        fetch("EmpleadoServlet",
                {method: 'POST', body: personaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Empleado.modalEd(datotexto);
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
        fetch("EmpleadoServlet",
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
                                    <th class='text-center'>Usuario</th>\n\
                                    <th class='text-center'>Acciones</th>\n\
                                    </tr>";
                        let personas = JSON.parse(datotexto);
                        for (let i = 0; i < personas.length; i++) {
                            document.querySelector("#listado").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ personas[i].id +"</td>\n\
                                    <td class='text-center'>"+ personas[i].nombre +"</td>\n\
                                    <td class='text-center'>"+ personas[i].apellido +"</td>\n\
                                    <td class='text-center'>"+ personas[i].usuario +"</td>\n\
                                    <td class='text-center'>\n\
                                    <button type='button' class='btn btn-warning btn-sm' onclick='Empleado.editar(" + personas[i].id + ");'>Editar</button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Empleado.eliminar(" + personas[i].id + ");'>\n\
                                    Eliminar\n\
                                    </button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Empleado.verEmpleado(" + personas[i].id + ");'>\n\
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
        fetch("EmpleadoServlet?&q=" + paramId,
                {method: 'DELETE'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    Empleado.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static verEmpleado(empleado){
        fetch("EmpleadoServlet?&q=" + empleado,
                {method: 'GET'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    let empleado = JSON.parse(datotexto);
                    document.querySelector("#listado").innerHTML =
                            "<br><h5 class='modal-title'>Informacion del Empleado</h5>\n\
                            <br>\n\
                            <div class='list-group'>\n\
                                <div class='list-group'>\n\
                                    <li class='list-group-item'><b>Nombre: </b>" + empleado.nombre + "</li>\n\
                                    <li class='list-group-item'><b>Apellido: </b>" + empleado.apellido + "</li>\n\
                                    <li class='list-group-item'><b>DNI: </b>" + empleado.dni + "</li>\n\
                                    <li class='list-group-item'><b>Telefono: </b>" + empleado.telefono + "</li>\n\
                                    <li class='list-group-item'><b>Usuario: </b>" + empleado.usuario + "</li>\n\
                                    <li class='list-group-item'><b>Direccion: </b>" + empleado.direccion + "</li>\n\
                                    <li class='list-group-item'><b>Mail: </b>" + empleado.mail + "</li>\n\
                                    <li class='list-group-item'><b>Salario: </b>" + empleado.salario + "</li>\n\
                                </div>\n\
                            </div>";
                    });
    }
    
    static editar(id){
        fetch("EmpleadoServlet?&q=" + id,
                {method: 'GET'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    let empleado = JSON.parse(datotexto);
                    document.querySelector("#listado").innerHTML =
                            "<br><h5 class='modal-title'>Informacion del Empleado</h5>\n\
                            <br>\n\
                            <div class='list-group'>\n\
                                <div class='list-group'>\n\
                                    <li class='list-group-item' id='id2' value="+empleado.id +"><b>ID: </b>" + empleado.id + "</li>\n\
                                    <li class='list-group-item'><b>Nombre: </b><input type='text' id='nombre2' value=" + empleado.nombre + "></li>\n\
                                    <li class='list-group-item'><b>Apellido: </b><input id='apellido2' value=" + empleado.apellido + "></li>\n\
                                    <li class='list-group-item'><b>DNI: </b><input id='dni2' value=" + empleado.dni + "></li>\n\
                                    <li class='list-group-item'><b>Telefono: </b><input id='telefono2' value=" + empleado.telefono + "></li>\n\
                                    <li class='list-group-item'><b>Usuario: </b><input id='usuario2' value=" + empleado.usuario + "></li>\n\
                                    <li class='list-group-item'><b>Password: </b><input id='password2' value=" + empleado.password + "></li>\n\
                                    <li class='list-group-item'><b>Direccion: </b><input id='direccion2' value=" + empleado.direccion + "></li>\n\
                                    <li class='list-group-item'><b>Mail: </b><input id='mail2' value=" + empleado.mail + "></li>\n\
                                    <li class='list-group-item'><b>Salario: </b><input id='salario2' value=" + empleado.salario + "></li>\n\
                                </div>\n\
                            </div>\n\
                                <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Empleado.editado();'>\n\
                                    Editar\n\
                                </button>";
                    });
    }
    
    static editado(){
        let empleado = {};
        empleado.id = document.getElementById("id2").value;
        empleado.nombre = document.getElementById("nombre2").value;
        empleado.apellido = document.getElementById("apellido2").value;
        empleado.dni = document.getElementById("dni2").value;
        empleado.telefono = document.getElementById("telefono2").value;
        empleado.usuario = document.getElementById("usuario2").value;
        empleado.password = document.getElementById("password2").value;
        empleado.direccion = document.getElementById("direccion2").value;
        empleado.mail = document.getElementById("mail2").value;
        empleado.salario = document.getElementById("salario2").value;
        
        let personaJSON = JSON.stringify(empleado);

        fetch("EmpleadoServlet",
                {method: 'PUT', body: personaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Empleado.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
}


Empleado.inicializar();