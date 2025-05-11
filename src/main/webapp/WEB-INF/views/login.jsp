<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="includes/head.jsp"/>
    <!-- Incluye Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />
    
    <div class="container mt-5">
        <h2 class="text-center">Inicio de Sesión</h2>

        <!-- Mostrar mensaje de error si existe -->
        <%
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) {
        %>
            <div class="alert alert-danger" role="alert">
                <%= error %>
            </div>
        <%
            }
        %>

        <form action="login" method="post" class="mt-4">
            <div class="form-group">
                <label for="correoUsuario">Correo Electrónico:</label> 
                <input type="email" class="form-control" id="correoUsuario" name="correoUsuario" required>
            </div>

            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" class="form-control" id="contrasena" name="contrasena" required>
            </div>

            <button type="submit" class="btn btn-productos btn-block">Iniciar Sesión</button>
        </form>

        <p class="mt-3 text-center">¿No tienes cuenta? <a href="<%= request.getContextPath() %>/registro">Regístrate aquí</a></p>
    </div>

    <jsp:include page="includes/footer.jsp" />
    <!-- Incluye Bootstrap JS y dependencias -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
