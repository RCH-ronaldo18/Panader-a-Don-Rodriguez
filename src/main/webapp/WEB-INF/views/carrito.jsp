<%@ page import="model.Carrito" %>
<%@ page import="model.Producto" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    // Obtener el carrito de la sesión
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    double totalGeneral = 0.0; // Inicializar el total general
%>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp"/>

<body>
<header>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="container-soporte">
                    <i class="fa-solid fa-location-dot"></i>
                    <div class="content-soporte">
                        <span>Ubicación:</span> <span id="span1">Lima, San Juan de Lurigancho</span>
                    </div>
                </div> 
            </div>
            <div class="col-4 img-logo">
                <img src="img/1.png" class="d-block w-100 mx-auto" style="max-width: 60px;" alt="...">
            </div>
            <div class="col-4 text-end">
                <c:choose>
                    <c:when test="${not empty nombreUsuario}">
                        <div class="dropdown me-2">
                            <button class="btn btn-secondary dropdown-toggle" type="button"
                                id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fa-solid fa-user"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown" style="z-index: 1050;">
                                <li><a class="dropdown-item" href="#">ID de Usuario: ${idUsuario}</a></li>
                                <li><a class="dropdown-item" href="#">Correo de Usuario: ${correoUsuario}</a></li>
                                <li><a class="dropdown-item" href="#">Rol: ${idTipoUsuario == '1' ? 'ADMINISTRADOR' : idTipoUsuario == '2' ? 'VENDEDOR' : idTipoUsuario == '3' ? 'USUARIO' : ''}</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cerrarSesion">Cerrar sesión</a></li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                            <i class="fa-solid fa-right-to-bracket"></i> Iniciar sesión
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</header>

<nav class="navbar navbar-expand-md">
    <div class="container">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarCollapse" aria-controls="navbarCollapse"
            aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mx-auto mb-md-0">
                <li class="nav-item"><a class="nav-link" aria-current="page"
                    href="${pageContext.request.contextPath}/inicio">INICIO</a></li>
                <li><a class="nav-link" aria-current="page"
                    href="${pageContext.request.contextPath}/desayuno">DESAYUNO</a></li>
                <li><a class="nav-link" aria-current="page"
                    href="${pageContext.request.contextPath}/postres">POSTRES</a></li>
                <li><a class="nav-link" aria-current="page"
                    href="${pageContext.request.contextPath}/mini-dulces">MINI DULCES</a></li>
                <li><a class="nav-link" aria-current="page"
                    href="${pageContext.request.contextPath}/mini-tortas">MINI TORTAS</a></li>
            </ul>
        </div>
        <a href="${pageContext.request.contextPath}/carrito"> <i class="fa-solid fa-cart-shopping"></i> </a>
    </div>
</nav>

<h2>Carrito de Compras</h2>
<div class="container">
    <% if (carrito != null && !carrito.getProductos().isEmpty()) { %>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Producto ID</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>SubTotal</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        for (Map.Entry<Integer, Integer> entry : carrito.getProductos().entrySet()) {
                            int idProducto = entry.getKey();
                            int cantidad = entry.getValue();
                            String nombre = carrito.getNombreProducto(idProducto);
                            double precio = carrito.getPrecioProducto(idProducto);
                            double total = precio * cantidad;
                            totalGeneral += total; // Sumar el total de este producto al total general
                    %>
                        <tr data-id="<%= idProducto %>">
                            <td><%= idProducto %></td>
                            <td><%= nombre %></td>
                            <td>S/<%= precio %></td>	
                            <td>
                                <input type="number" value="<%= cantidad %>" min="1" class="cantidad" data-id="<%= idProducto %>" data-precio="<%= precio %>">
                            </td>
                            <td class="total">S/<%= total %></td>
                            <td>
                                <button class="btn btn-danger eliminar" data-id="<%= idProducto %>">Eliminar</button>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4" class="text-end fs-3"><strong>Total General:</strong></td>
                        <td class="total-general fs-4">S/<%= totalGeneral %></td>
                    </tr>
                </tfoot>
            </table>
        </div>
<div class="text-end">
    <form action="${pageContext.request.contextPath}/registrarVenta" method="post" id="formRegistrarCompra" class="d-inline">
        <input type="hidden" name="idCliente" value="${idUsuario}">
        <input type="hidden" name="total" value="<%= totalGeneral %>"> <!-- Pasar el total general -->
        <button type="button" id="btnRegistrarCompra" class="btn btn-success">Registrar Compra</button>
    </form>
    <form action="carrito" method="post" class="d-inline">
        <button type="submit" name="action" value="vaciar" class="btn btn-warning">Vaciar Carrito</button>
    </form>
</div>

    <% } else { %>
        <p>El carrito está vacío.</p>
    <% } %>
</div>
<!-- Modal de Bootstrap -->
<div class="modal fade" id="modalIniciarSesion" tabindex="-1" aria-labelledby="modalIniciarSesionLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-danger" id="modalIniciarSesionLabel">¡Atención!</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Necesitas iniciar sesión para realizar una compra.
            </div>
            <div class="modal-footer">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Iniciar Sesión</a>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<!-- MODAL DE STOCK INSUFICIENTE -->
<!-- Modal -->
<div id="modalError" class="modal" tabindex="-1" aria-labelledby="modalErrorLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalErrorLabel">Lo sentimos!</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="modalErrorMessage"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<script>
    // Mostrar el modal si hay un error
    window.onload = function() {
        var errorMessage = "${error}";
        if (errorMessage) {
            // Establecer el mensaje del error en el modal
            document.getElementById("modalErrorMessage").innerText = errorMessage;

            // Mostrar el modal
            var modal = new bootstrap.Modal(document.getElementById('modalError'));
            modal.show();
        }
    };
</script>


<jsp:include page="includes/footer.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function () {
    $('.cantidad').on('change', function () {
        var idProducto = $(this).data('id');
        var nuevaCantidad = parseInt($(this).val());

        // Realizar una solicitud AJAX para actualizar el carrito
        $.ajax({
            url: 'carrito',
            method: 'POST',
            data: {
                action: 'actualizar',
                id: idProducto,
                cantidad: nuevaCantidad
            },
            success: function () {
                console.log('Producto actualizado en el servidor');
            },
            error: function () {
                alert('Error al actualizar el producto en el servidor');
            }
        });

        // Actualizar el total localmente
        var precio = parseFloat($(this).data('precio'));
        var nuevoTotal = precio * nuevaCantidad;
        $(this).closest('tr').find('.total').text('S/' + nuevoTotal.toFixed(2));

        actualizarTotalGeneral();
    });

    $('.eliminar').on('click', function () {
        var idProducto = $(this).data('id');

        // Realizar una solicitud AJAX para eliminar el producto
        $.ajax({
            url: 'carrito',
            method: 'POST',
            data: {
                action: 'eliminar',
                id: idProducto
            },
            success: function () {
                console.log('Producto eliminado en el servidor');
            },
            error: function () {
                alert('Error al eliminar el producto en el servidor');
            }
        });

        // Eliminar la fila localmente
        $(this).closest('tr').remove();
        actualizarTotalGeneral();
    });

    function actualizarTotalGeneral() {
        var totalGeneral = 0;
        $('.total').each(function () {
            totalGeneral += parseFloat($(this).text().replace('S/', ''));
        });
        $('.total-general').text('S/' + totalGeneral.toFixed(2));

        // Calcular el IGV y el total con IGV
        var igv = totalGeneral * 0.18;
        var totalConIgv = totalGeneral + igv;
        $('.igv').text('S/' + igv.toFixed(2));
        $('.total-con-igv').text('S/' + totalConIgv.toFixed(2));
    }

});
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const btnRegistrarCompra = document.getElementById("btnRegistrarCompra");
        const formRegistrarCompra = document.getElementById("formRegistrarCompra");

        btnRegistrarCompra.addEventListener("click", function () {
            // Verificar si el usuario ha iniciado sesión
            const idUsuario = "${idUsuario}"; // Variable JSP para identificar al usuario

            if (!idUsuario.trim()) {
                // Mostrar el modal si no ha iniciado sesión
                const modal = new bootstrap.Modal(document.getElementById('modalIniciarSesion'));
                modal.show();
            } else {
                // Enviar el formulario si ha iniciado sesión
                formRegistrarCompra.submit();
            }
        });
    });
</script>


</body>
</html>

	