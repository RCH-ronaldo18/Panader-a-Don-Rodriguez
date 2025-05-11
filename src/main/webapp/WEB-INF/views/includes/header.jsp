<header>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="container-soporte">
                    <i class="fa-solid fa-location-dot"></i>
                    <div class="content-soporte">
                        <span>Ubicacion:</span> <span id="span1">Lima, San Juan de Lurigancho</span>
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
				</li>
				<a class="nav-link" aria-current="page"
					href="${pageContext.request.contextPath}/desayuno">DESAYUNO</a>
				</li>
				<li><a class="nav-link" aria-current="page"
					href="${pageContext.request.contextPath}/postres">POSTRES</a></li>
				<li><a class="nav-link" aria-current="page"
					href="${pageContext.request.contextPath}/mini-dulces">MINI
						DULCES</a></li>
				<li><a class="nav-link" aria-current="page"
					href="${pageContext.request.contextPath}/mini-tortas">MINI
						TORTAS</a></li>
			</ul>
		</div>
		<a href="${pageContext.request.contextPath}/carrito"> <i class="fa-solid fa-cart-shopping"></i>
		</a>
	</div>
</nav>