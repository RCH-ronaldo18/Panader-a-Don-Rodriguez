<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ page import="model.Producto"%>
<%@ page import="java.util.List" %>
<html lang="es">
<head>
    <jsp:include page="includes/head.jsp"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- Asegúrate de incluir jQuery -->
</head>
<body>
    <jsp:include page="includes/header.jsp" />
    <div class="container">
        <h2 class="my-3">DESAYUNOS</h2>
        <div class="row">
            <%
                // Obtener la lista de productos del atributo
                List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");

                // Verificar si hay productos
                if (listaProductos != null && !listaProductos.isEmpty()) {
                    for (Producto producto : listaProductos) {
            %>
                <div class="col-lg-3 col-md-4 col-sm-6 mt-2">
                    <div class="card">
                        <img src="img/PRODUCTOS/<%= producto.getId_producto() %>.png" class="card-img-top" alt="<%= producto.getNombre() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= producto.getNombre() %></h5>
                            <p class="card-text"><%= producto.getDescripcion() %></p>
                            <p class="text-center"><strong>Precio: </strong>S/<%= producto.getPrecio() %> UND</p>
                            
                            <form action="carrito" method="post" class="text-center">
                                <input type="hidden" name="id" value="<%= producto.getId_producto() %>">
                                <input type="hidden" name="nombre" value="<%= producto.getNombre() %>">
                                <input type="hidden" name="precio" value="<%= producto.getPrecio() %>">
                                <div class="input-group mb-2" style="width: 80%; margin: auto;">
                                    <button type="button" class="btn btn-outline-secondary" onclick="decrementarCantidad(this)">-</button>
                                    <input type="number" name="cantidad" value="1" min="1" class="form-control cantidad-input" style="text-align: center;">
                                    <button type="button" class="btn btn-outline-secondary" onclick="incrementarCantidad(this)">+</button>
                                </div>
                                <button type="submit" name="action" value="agregar" class="btn btn-primary">AGREGAR AL CARRITO</button>
                            </form>
                            
                        </div>
                    </div>
                </div>
            <%
                    }
                } else {
            %>
                <div class="col-12">
                    <p class="text-center">No hay productos disponibles en esta categoría.</p>
                </div>
            <%
                }
            %>
        </div>
    </div>
    <jsp:include page="includes/footer.jsp" />
    
    <script>
        function incrementarCantidad(button) {
            var input = $(button).siblings('.cantidad-input');
            var cantidadActual = parseInt(input.val());
            input.val(cantidadActual + 1);
        }

        function decrementarCantidad(button) {
            var input = $(button).siblings('.cantidad-input');
            var cantidadActual = parseInt(input.val());
            if (cantidadActual > 1) {
                input.val(cantidadActual - 1);
            }
        }
    </script>
</body>
</html>

