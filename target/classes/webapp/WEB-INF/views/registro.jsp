<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <jsp:include page="includes/head.jsp" />
</head>

<body>
    <jsp:include page="includes/header.jsp" />

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h2 class="text-center mt-5">Registro de Usuario</h2>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger text-center" role="alert">
                        ${error}
                    </div>
                </c:if>

                <form action="registro" method="post">
                    <fieldset class="mb-4">
                        <legend class="w-auto px-2 fw-bold">Datos de inicio de sesión</legend>
                        <div class="mb-3">
                            <label for="correoUsuario" class="form-label">Correo:</label>
                            <input type="email" class="form-control" id="correoUsuario" name="correoUsuario"
                                   placeholder="Ingrese correo" required>
                        </div>
                        <div class="mb-3">
                            <label for="contrasena" class="form-label">Contraseña:</label>
                            <input type="password" class="form-control" id="contrasena" name="contrasena"
                                   placeholder="Ingrese contraseña" required>
                        </div>
                    </fieldset>

                    <fieldset class="mb-4">
                        <legend class="fw-bold w-auto px-2">Datos Personales</legend>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre:</label>
                            <input type="text" class="form-control" id="nombre" name="nombre"
                                   placeholder="Ingrese su nombre" required>
                        </div>
                        <div class="mb-3">
                            <label for="direccion" class="form-label">Dirección:</label>
                            <input type="text" class="form-control" id="direccion" name="direccion"
                                   placeholder="Ingrese su dirección" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono:</label>
                            <input type="text" class="form-control" id="telefono" name="telefono"
                                   placeholder="Ingrese su teléfono" required>
                        </div>
                    </fieldset>

                    <button type="submit" class="btn btn-primary w-100">Registrarse</button>
                </form>

                <p class="text-center mt-3">¿Ya tienes cuenta?
                    <a href="${pageContext.request.contextPath}/login">Inicia sesión aquí</a>
                </p>
            </div>
        </div>
    </div>

    <!-- Modal de éxito -->
    <c:if test="${sessionScope.registroExitoso}">
        <div class="modal fade" id="registroModal" tabindex="-1" aria-labelledby="registroModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registroModalLabel">Registro Exitoso</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        ¡Te has registrado exitosamente! ¿Ya tienes cuenta? <br>
                        <a href="${pageContext.request.contextPath}/login">Inicia sesión aquí</a>.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var registroModal = new bootstrap.Modal(document.getElementById('registroModal'));
                registroModal.show();
            });
        </script>

        <!-- Limpia el flag de sesión para no mostrar la modal otra vez -->
        <c:remove var="registroExitoso" scope="session"/>
    </c:if>

    <jsp:include page="includes/footer.jsp" />
</body>

</html>
