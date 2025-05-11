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
		<h2 class="text-center mb-4">Registrar Nuevo Usuario Administrador</h2>
		            <!-- Mensaje de error si ocurre -->
		<div class="row justify-content-center">
			<div class="col-md-6">
					        <c:if test="${not empty error}">
                <div class="alert alert-danger text-center" role="alert">
                    ${error}
                </div>
            </c:if>
				<form action="usuarios?action=guardar" method="post">
					<div class="form-group mb-3">
						<label for="correoUsuario">Correo:</label> <input type="email"
							class="form-control" id="correoUsuario" name="correoUsuario"
							required>
					</div>

					<div class="form-group mb-3">
						<label for="contrasena">Contrase�a:</label> <input type="password"
							class="form-control" id="contrasena" name="contrasena" required>
					</div>

					<div class="form-group mb-3">
						<label for="idTipoUsuario">Tipo de Usuario:</label> <select
							class="form-control" id="idTipoUsuario" name="idTipoUsuario"
							required>
							<option value="1">Administrador</option>
							<option value="2">Vendedor</option>
							<option value="3">Usuario</option>
						</select>
					</div>


					<div class="form-group mb-3">
						<label for="nombreCliente">Nombre Cliente:</label> <input
							type="text" class="form-control" id="nombreCliente"
							name="nombre" required>
					</div>

					<div class="form-group mb-3">
						<label for="direccion">Direcci�n:</label> <input type="text"
							class="form-control" id="direccion" name="direccion" required>
					</div>

					<div class="form-group mb-3">
						<label for="telefono">Tel�fono:</label> <input type="text"
							class="form-control" id="telefono" name="telefono" required>
					</div>

					<button type="submit" class="btn btn-primary w-100">Guardar</button>
				</form>
				<div class="mt-3">
					<a href="/Web_donRODRIGUEZ/usuarios" class="btn btn-secondary w-100">Volver
						a la Lista</a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp" />
</body>
</html>
