<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.Producto"%>
<%@ page import="java.util.List"%>
<html>
<head>
<jsp:include page="includes/head.jsp" />
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 mt-2">
				<nav class="sidebar  d-flex flex-column p-3">
					<h4 class="text-black text-center">VENDEDOR</h4>
					<ul class="nav flex-column">
						<li class="nav-item"><a href="#" class="nav-link">Dashboard</a>
						</li>
						<li><a class="nav-link" aria-current="page"
							href="${pageContext.request.contextPath}/productos">Productos</a>
						</li>
						<li class="nav-item"><a href="#" class="nav-link">Ordenes</a>
						</li>
						<li class="nav-item"><a href="#" class="nav-link">Reportes</a>
						</li>
					</ul>
				</nav>
			</div>
			<div class="col-md-9">
				<h4 class="text-center">Bienvenido al panel de vendedor</h4>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp" />
</body>
</html>