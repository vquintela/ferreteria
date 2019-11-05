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

class Marcas {
    static iniciar(){
        fetch("MarcaServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        document.querySelector("#listado_marcas").innerHTML =
                                    "<br>\n\
                                    <tr>\n\
                                    <th class='text-center'>ID</th>\n\
                                    <th class='text-center'>Marca</th>\n\
                                    <th class='text-center'>Acciones</th>\n\
                                    </tr>";
                        let marcas = JSON.parse(datotexto);
                        for (let i = 0; i < marcas.length; i++) {
                            document.querySelector("#listado_marcas").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ marcas[i].id +"</td>\n\
                                    <td class='text-center'>"+ marcas[i].marca +"</td>\n\
                                    <td class='text-center'>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Marcas.eliminar(" + marcas[i].id + ");'>\n\
                                    Eliminar\n\
                                    </button>\n\
                                    </td></tr>";
                        }   
                    }catch(Excepcion){
                        Marcas.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static agregar(){
        let marca = {};
        marca.marca = document.getElementById("marca_nombre").value;
        
        let marcaJSON = JSON.stringify(marca);
        
        fetch("MarcaServlet",
                {method: 'POST', body: marcaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static eliminar(paramId){
        fetch("MarcaServlet?&q=" + paramId,
                {method: 'DELETE'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
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
}

class Categoria {
    static iniciar(){
        fetch("CategoriaServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        document.querySelector("#listado_categorias").innerHTML =
                                    "<br>\n\
                                    <tr>\n\
                                    <th class='text-center'>ID</th>\n\
                                    <th class='text-center'>Nombre</th>\n\
                                    <th class='text-center'>Descripcion</th>\n\
                                    <th class='text-center'>Acciones</th>\n\
                                    </tr>";
                        let categoria = JSON.parse(datotexto);
                        for (let i = 0; i < categoria.length; i++) {
                            document.querySelector("#listado_categorias").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ categoria[i].id +"</td>\n\
                                    <td class='text-center'>"+ categoria[i].nombre +"</td>\n\
                                    <td class='text-center'>"+ categoria[i].descripcion +"</td>\n\
                                    <td class='text-center'>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Categoria.eliminar(" + categoria[i].id + ");'>\n\
                                    Eliminar\n\
                                    </button>\n\
                                    </td></tr>";
                        }   
                    }catch(Excepcion){
                        Marcas.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static agregar(){
        let categoria = {};
        categoria.nombre = document.getElementById("categoria_nombre").value;
        categoria.descripcion = document.getElementById("categoria_descripcion").value;
        
        let categoriaJSON = JSON.stringify(categoria);
        
        fetch("CategoriaServlet",
                {method: 'POST', body: categoriaJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static eliminar(paramId){
        fetch("CategoriaServlet?&q=" + paramId,
                {method: 'DELETE'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
}

class Producto {
    static iniciar(){
        fetch("ProductoServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        document.querySelector("#listado_productos").innerHTML =
                                    "<br>\n\
                                    <tr>\n\
                                    <th class='text-center'>ID</th>\n\
                                    <th class='text-center'>Categoria</th>\n\
                                    <th class='text-center'>Marca</th>\n\
                                    <th class='text-center'>Nombre</th>\n\
                                    <th class='text-center'>Costo</th>\n\
                                    <th class='text-center'>P. Unitario</th>\n\
                                    <th class='text-center'>Stock</th>\n\
                                    <th class='text-center'>Acciones</th>\n\
                                    </tr>";
                        let productos = JSON.parse(datotexto);
                        for (let i = 0; i < productos.length; i++) {
                            document.querySelector("#listado_productos").innerHTML +=
                                    "<tr id='listado'>\n\
                                    <td class='text-center'>"+ productos[i].id +"</td>\n\
                                    <td class='text-center'>"+ productos[i].categoria.nombre +"</td>\n\
                                    <td class='text-center'>"+ productos[i].marca.marca +"</td>\n\
                                    <td class='text-center'>"+ productos[i].nombre +"</td>\n\
                                    <td class='text-center'>"+ productos[i].costo +"</td>\n\
                                    <td class='text-center'>"+ productos[i].precioUnitario +"</td>\n\
                                    <td class='text-center'>"+ productos[i].stock +"</td>\n\
                                    <td class='text-center'>\n\
                                    <button type='button' class='btn btn-warning btn-sm' onclick='Producto.editar(" + productos[i].id + ");'>Editar</button>\n\
                                    <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Producto.eliminar(" + productos[i].id + ");'>\n\
                                    Eliminar\n\
                                    </button>\n\
                                    </td></tr>";
                        }   
                    }catch(Excepcion){
                        Marcas.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static agregar() {
        let cat = document.getElementById("categoriaProducto").value;
        cat = cat.split(",");
        let marc = document.getElementById("marcasProducto").value;
        marc = marc.split(",");
        let marca = {};
        marca.id = marc[0];
        marca.marca = marc[1];
        let categoria = {};
        categoria.id = cat[0];
        categoria.nombre = cat[1];
        categoria.descripcion = cat[2];
        let producto = {};
        producto.nombre = document.getElementById("nombreProducto").value;
        producto.costo = document.getElementById("costoProducto").value;
        producto.precioUnitario = document.getElementById("precioUnitarioProducto").value;
        producto.stock = document.getElementById("stockProducto").value;
        producto.marca = marca;
        producto.categoria = categoria;
        
        let productoJSON = JSON.stringify(producto);
        
        fetch("ProductoServlet",
                {method: 'POST', body: productoJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static inicializar(param1, param2){
        fetch("MarcaServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        let marcas = JSON.parse(datotexto);
                        for (let i = 0; i < marcas.length; i++) {
                            document.querySelector(param1).innerHTML +=
                                    "<option value="+ Object.values(marcas[i]) +">"+ marcas[i].marca +"</option>";
                        }   
                    }catch(Excepcion){
                        Marcas.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
        
        fetch("CategoriaServlet",
                {method: 'GET'})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    try{
                        let categorias = JSON.parse(datotexto);
                        for (let i = 0; i < categorias.length; i++) {
                            document.querySelector(param2).innerHTML +=
                                    "<option value="+ Object.values(categorias[i]) +">"+ categorias[i].nombre +"</option>";
                        }   
                    }catch(Excepcion){
                        Marcas.modalEd(datotexto);
                    }
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static eliminar(paramId){
        fetch("ProductoServlet?&q=" + paramId,
                {method: 'DELETE'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
    
    static editar(id){
        fetch("ProductoServlet?&q=" + id,
                {method: 'GET'})
                .then(function (response){
                    return response.text();
                })
                .then(function (datotexto) {
                    let producto = JSON.parse(datotexto);
                    document.querySelector("#listado_productos").innerHTML =
                            "<br><h5 class='modal-title'>Informacion del Producto</h5>\n\
                            <br>\n\
                            <div class='form-signin'>\n\
                                <div class='form-row'>\n\
                                    <div class='form-group col-md-6'>\n\
                                        <label for='id2'>ID:</label>\n\
                                        <input type='text' class='form-control' id='id2' value="+producto.id +">\n\
                                    <label for='nombre2'>Nombre:</label>\n\
                                        <input type='text' class='form-control' id='nombre2' value="+producto.nombre +">\n\
                                    <label for='costo2'>Costo:</label>\n\
                                        <input type='text' class='form-control' id='costo2' value="+producto.costo +">\n\
                                    <label for='precioUnitario'>Precio Unitario:</label>\n\
                                        <input type='text' class='form-control' id='precioUnitario2' value="+producto.precioUnitario +">\n\
                                </div>\n\
                                <div class='form-group col-md-6'>\n\
                                    <label for='stock'>Stock:</label>\n\
                                    <input type='text' class='form-control' id='stock2' value="+producto.stock +">\n\
                                    <label for='marcasProducto3'>Marca:</label>\n\
                                    <select name='marcasProducto3' id='marcasProducto3' class='form-control'>\n\
                                    <option>Marca</option>\n\
                                    </select>\n\
                                    <br>\n\
                                    <label for='categoriaProducto3'>Categoria:</label>\n\
                                    <select name='categoriaProducto3' id='categoriaProducto3' class='form-control'>\n\
                                    <option>Categoria</option>\n\
                                    </select>\n\
                                </div>\n\
                            </div>\n\
                                <button type='button' class='btn btn-danger btn-sm eliminar' onclick='Producto.editado();'>\n\
                                    Editar\n\
                                </button>";
                    Producto.inicializar("#marcasProducto3", "#categoriaProducto3");
                    });
    }
    
    static editado() {
        let cat = document.getElementById("categoriaProducto3").value;
        cat = cat.split(",");
        let marc = document.getElementById("marcasProducto3").value;
        marc = marc.split(",");
        let marca = {};
        marca.id = marc[0];
        marca.marca = marc[1];
        let categoria = {};
        categoria.id = cat[0];
        categoria.nombre = cat[1];
        categoria.descripcion = cat[2];
        let producto = {};
        producto.id = document.getElementById("id2").value;
        producto.nombre = document.getElementById("nombre2").value;
        producto.costo = document.getElementById("costo2").value;
        producto.precioUnitario = document.getElementById("precioUnitario2").value;
        producto.stock = document.getElementById("stock2").value;
        producto.marca = marca;
        producto.categoria = categoria;
        
        let productoJSON = JSON.stringify(producto);
        
        fetch("ProductoServlet",
                {method: 'PUT', body: productoJSON})
                .then(function (response) {
                    return response.text();
                })
                .then(function (datotexto) {
                    Marcas.modalEd(datotexto);
                    document.querySelector("#aceptarEditarButton").addEventListener('click', function (){
                        location.reload();
                    });
                });
    }
}

Marcas.iniciar();
Categoria.iniciar();
Producto.iniciar();
Producto.inicializar("#marcasProducto", "#categoriaProducto");

