<%@ page import="java.util.List" %>
<%@ page import="model.Venta" %>
<%@ page import="model.DetalleVenta" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
<h2>Mis Compras</h2>
<% List<Venta> ventas = (List<Venta>) request.getAttribute("ventas"); %>
<% if (ventas != null && !ventas.isEmpty()) { %>
    <table class="table">
        <thead>
            <tr>
                <th>ID Venta</th>
                <th>Fecha</th>
                <th>Total</th>
                <th>Detalles</th>
            </tr>
        </thead>
        <tbody>
            <% for (Venta venta : ventas) { %>
                <tr>
                    <td><%= venta.getIdVenta() %></td>
                    <td><%= venta.getFechaVenta() %></td>
                    <td>S/<%= venta.getTotal() %></td>
                    <td>
                        <a href="verDetallesVenta?idVenta=<%= venta.getIdVenta() %>" class="btn btn-info">Ver Detalles</a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
<% } else { %>
    <p>No tienes compras registradas.</p>
<% } %>

<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>

