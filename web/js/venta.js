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
        
        fetch("VentaServlet",
                {method: 'POST', body: ventaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    let lista = [];
                    venta.id = datotexto;
                    Venta.agregarDetalle(venta, lista);
                });
    }
    
    static finalizarVenta(lista){
        fetch("DetalleVentaServlet",
                {method: 'POST', body: lista})
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
    
    static agregarDetalle(venta, lista){
        document.querySelector("#venta_detalle").innerHTML =
                                    "<br>\n\
                                    <div class='form-group col-md-6'>\n\
                                    <label for='cliente'>Cliente:</label>\n\
                                    <input type='text' readonly='cliente' class='form-control' id='cliente' placeholder='Nombre: "+venta.cliente.nombre +" ,Apellido: "+ venta.cliente.apellido+"'>\n\
                                    <label for='empleado'>Empleado:</label>\n\
                                    <input type='text' readonly='empleado' class='form-control' id='empleado' placeholder='Nombre: "+venta.empleado.nombre +" ,Apellido: "+ venta.empleado.apellido+"'>\n\
                                    <div class='form-group col-md-6'>\n\
                                    <label for='producto_detalle'>Producto:</label>\n\
                                    <select name='producto_detalle' id='producto_detalle' class='form-control'>\n\
                                    <option>Producto</option>\n\
                                    </select>\n\
                                    <label for='cantidad_detalle'>Cantidad:</label>\n\
                                    <input type='text' class='form-control' id='cantidad_detalle' placeholder='Cantidad'>\n\
                                    </div>\n\
                                    <br>\n\
                                    <br>\n\
                                    <button type='button' class='btn btn-warning btn-sm' id='agregar_detalle'>Agregar detalle</button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' id='cancelar_detalle'>Finalizar</button></div>\n\
                                    <br>\n\
                                    <br>\n\
                                    <br>\n\
                                    <tr>\n\
                                    <table class='table table-bordered' id='listado_detalleProductos'>\n\
                                    <th class='text-center'>Producto</th>\n\
                                    <th class='text-center'>Cantidad</th>\n\
                                    <th class='text-center'>Precio</th>\n\
                                    </table>\n\
                                    </tr>";
            for (let i = 0; i < lista.length; i++) {
                            document.querySelector("#listado_detalleProductos").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ lista[i].producto.categoria.nombre +"</td>\n\
                                    <td class='text-center'>"+ lista[i].cantidad +"</td>\n\
                                    <td class='text-center'>"+ lista[i].precio +"</td></tr>";
                        }
            Venta.agregarProductos();               
            let elemAgregar = document.querySelector("#agregar_detalle");
            elemAgregar.addEventListener('click', function(){
                let detalleVenta = {};
                detalleVenta.venta = venta;
                let prod = document.getElementById("producto_detalle").value;
                let producto;
                fetch("ProductoServlet?&q=" + prod,
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    producto = JSON.parse(datotexto);
                    detalleVenta.producto = producto;
                    detalleVenta.cantidad = document.getElementById("cantidad_detalle").value;
                    detalleVenta.precio = detalleVenta.cantidad * detalleVenta.producto.costo;
                    lista[lista.length] = detalleVenta;
                    Venta.agregarDetalle(venta, lista);
                });
            });
            let elemCancelar = document.querySelector("#cancelar_detalle");
            elemCancelar.addEventListener('click', function(){Venta.finalizarVenta(JSON.stringify(lista));});
    }
    
    static agregarProductos(){
        fetch("ProductoServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        let productos = JSON.parse(datotexto);
                        for (let i = 0; i < productos.length; i++) {
                            document.querySelector("#producto_detalle").innerHTML +=
                                    "<option value="+ productos[i].id +">Nombre: "+ productos[i].nombre+", Precio Unitario: "+productos[i].precioUnitario+", Stock: "+ productos[i].stock +"</option>";
                        }   
                    }catch(Excepcion){
                        Venta.modalEd(datotexto);
                    }
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
    
    static listarDetalleVenta(){
        fetch("VentaServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        document.querySelector("#listadoVentas2").innerHTML =
                                    "<br>\n\
                                    <tr>\n\
                                    <th class='text-center'>ID</th>\n\
                                    <th class='text-center'>Dato Empleado</th>\n\
                                    <th class='text-center'>Dato Cliente</th>\n\
                                    <th class='text-center'>Fecha</th>\n\
                                    <th class='text-center'>Acciones</th>\n\
                                    </tr>";
                        let ventas = JSON.parse(datotexto);
                        for (let i = 0; i < ventas.length; i++) {
                            document.querySelector("#listadoVentas2").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ ventas[i].id +"</td>\n\
                                    <td class='text-center'>"+ ventas[i].empleado.nombre +"</td>\n\
                                    <td class='text-center'>"+ ventas[i].cliente.nombre +"</td>\n\
                                    <td class='text-center'>"+ ventas[i].fecha +"</td>\n\
                                    <td class='text-center'>\n\
                                    <button type='button' class='btn btn-warning btn-sm' onclick='Producto.editar(" + ventas[i].id + ");'>Editar</button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Producto.eliminar(" + ventas[i].id + ");'>\n\
                                    Cancelar\n\
                                    </button>\n\
                                    </td></tr>";
                        }   
                    }catch(Excepcion){
                        Venta.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
}

Venta.iniciar();