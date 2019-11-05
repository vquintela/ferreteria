/* global fetch*/

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

class Venta {
    static iniciar(){
        fetch("EmpleadoServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        let empleados = JSON.parse(datotexto);
                        for (let i = 0; i < empleados.length; i++) {
                            document.querySelector("#empleado_venta").innerHTML +=
                                    "<option value="+ Object.values(empleados[i]) +">"+ empleados[i].apellido +"</option>";
                        }   
                    }catch(Excepcion){
                        Venta.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
        
        fetch("ClienteServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        let clientes = JSON.parse(datotexto);
                        for (let i = 0; i < clientes.length; i++) {
                            document.querySelector("#cliente_venta").innerHTML +=
                                    "<option value="+ Object.values(clientes[i]) +">"+ clientes[i].apellido +"</option>";
                        }   
                    }catch(Excepcion){
                        Venta.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static agregar(){
        let clienteValor = document.getElementById("cliente_venta").value;
        clienteValor = clienteValor.split(",");
        let cliente = {};
        cliente.id = clienteValor[0];
        cliente.nombre = clienteValor[1];
        cliente.apellido = clienteValor[2];
        cliente.dni = clienteValor[3];
        cliente.mail = clienteValor[4];
        cliente.direccion = clienteValor[5];
        cliente.telefono = clienteValor[6];
        let empleadoValor = document.getElementById("empleado_venta").value;
        empleadoValor = empleadoValor.split(",");
        let empleado = {};
        empleado.id = empleadoValor[0];
        empleado.nombre = empleadoValor[1];
        empleado.apellido = empleadoValor[2];
        empleado.dni = empleadoValor[3];
        empleado.telefono = empleadoValor[4];
        empleado.usuario = empleadoValor[5];
        empleado.password = empleadoValor[6];
        empleado.direccion = empleadoValor[7];
        empleado.mail = empleadoValor[8];
        empleado.salario = empleadoValor[9];
        let fecha = document.getElementById("fecha_venta").value;
        let venta = {};
        venta.empleado = empleado;
        venta.cliente = cliente;
        venta.fecha = fecha;
        venta.cancelado = false;
        
        let ventaJSON = JSON.stringify(venta);
        let lista = [];
        Venta.agregarDetalle(ventaJSON, lista);
    }
    
    static finalizarVenta(ventaJSON, lista){
        fetch("VentaServlet",
                {method: 'POST', body: ventaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Venta.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static agregarDetalle(ventaJSON, lista){
        document.querySelector("#venta_detalle").innerHTML =
                                    "<br>\n\
                                    <label for='fecha_venta'>Fecha:</label>\n\
                                    <input type='text' class='form-control' id='fecha_venta' placeholder='Fecha' required>\n\
                                    <button type='button' class='btn btn-warning btn-sm' id='agregar_detalle'>Agregar detalle</button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' id='cancelar_detalle'>Finalizar</button>";
            let elemAgregar = document.querySelector("#agregar_detalle");
            elemAgregar.addEventListener('click', function(){
                let elemento = {};
                elemento.fecha = document.getElementById("fecha_venta").value;
                lista[lista.length] = elemento;
                Venta.agregarDetalle(ventaJSON, lista);
            });
            let elemCancelar = document.querySelector("#cancelar_detalle");
            elemCancelar.addEventListener('click', function(){Venta.finalizarVenta(ventaJSON, JSON.stringify(lista));});
    }
    
    static modalEd(texto) {
        let mascara = document.getElementById('lamascara');
       
        mascara.style.display = "block";
        document.querySelector('#panelResultados').innerHTML = texto;
    }
}

Venta.iniciar();