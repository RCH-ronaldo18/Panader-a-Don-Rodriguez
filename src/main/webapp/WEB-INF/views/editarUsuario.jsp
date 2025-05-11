<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Cliente" %>
<%
Integer userType = (Integer) session.getAttribute("idTipoUsuario");
if (userType == null || userType != 1) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />
<body>
    <jsp:include page="includes/header.jsp" />
    <div class="container mt-5">
        <h2 class="text-center mb-4">Editar Usuario Administrador</h2>
        
        <!-- Mensaje de error si ocurre -->
        <div class="row justify-content-center">
            <div class="col-md-6">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger text-center" role="alert">
                        ${error}
                    </div>
                </c:if>

                <%
                    Usuario usuario = (Usuario) request.getAttribute("usuario");
                    Cliente cliente = (Cliente) request.getAttribute("cliente");
                %>

                <form action="<%= request.getContextPath() %>/usuarios?action=actualizar" method="post">
                    <input type="hidden" name="idUsuario" value="<%= usuario.getId_usuario() %>">

                    <!-- Datos del usuario -->
                    <div class="form-group mb-3">
                        <label for="correoUsuario">Correo:</label>
                        <input type="email" class="form-control" name="correoUsuario" value="<%= usuario.getCorreo_usuario() %>" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="contrasena">Contraseña:</label>
                        <input type="password" class="form-control" name="contrasena">
                    </div>

                    <div class="form-group mb-3">
                        <label for="idTipoUsuario">Tipo de Usuario:</label>
                        <select class="form-control" name="idTipoUsuario" required>
                            <option value="1" <%= usuario.getId_tipo_usuario() == 1 ? "selected" : "" %>>Administrador</option>
                            <option value="2" <%= usuario.getId_tipo_usuario() == 2 ? "selected" : "" %>>Vendedor</option>
                            <option value="3" <%= usuario.getId_tipo_usuario() == 3 ? "selected" : "" %>>Usuario</option>
                        </select>
                    </div>

                    <!-- Datos del cliente -->
                    <div class="form-group mb-3">
                        <label for="nombre">Nombre Cliente:</label>
                        <input type="text" class="form-control" name="nombre" value="<%= cliente.getNombre() %>" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="direccion">Dirección:</label>
                        <input type="text" class="form-control" name="direccion" value="<%= cliente.getDireccion() %>" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="telefono">Teléfono:</label>
                        <input type="text" class="form-control" name="telefono" value="<%= cliente.getTelefono() %>" required>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Actualizar</button>
                </form>

                <div class="mt-3">
                    <a href="<%= request.getContextPath() %>/usuarios" class="btn btn-secondary w-100">Volver a la Lista</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
