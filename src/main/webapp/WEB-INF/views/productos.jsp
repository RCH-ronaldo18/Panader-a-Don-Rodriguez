<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.Producto"%>
<%@ page import="java.util.List"%>

<%
Integer userType = (Integer) session.getAttribute("idTipoUsuario");
if (userType == null || userType != 1) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>

<html>
<head>
<jsp:include page="includes/head.jsp" />
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<h2 class="text-center">Lista de Productos</h2>
	<div class="container-fluid">
		<div class="row">
<!-- Llamar al pandel de panelAdmin -->
<jsp:include page="includes/panelAdmin.jsp"></jsp:include>	
			<div class="col-md-9">
				<div class="row">
					<div class="col-md-8">
						<form action="productos" method="get"
							class="form-inline d-flex gap-3">
							<!-- Añadido d-flex y gap-3 -->
							<label for="categoria" class="mr-1">Filtrar por categoría:</label> <select name="categoria" id="categoria" class="form-control">
								<option value="">Todas</option>
								<option value="1">Desayuno</option>
								<option value="2">Postres</option>
								<option value="3">Mini Dulces</option>
								<option value="4">Mini Tortas</option>
							</select>
							<button type="submit" class="btn btn-primary">Filtrar</button>
						</form>

					</div>

					<div
						class="col-md-4 d-flex justify-content-center align-items-center">
						<a class="btn btn-info" href="productos?action=new">Agregar
							Producto</a>
					</div>

				</div>

				<div class="table-responsive">
					<table class="table">
						<tr>
							<th>ID</th>
							<th>Nombre</th>
							<th>Descripción</th>
							<th>Precio</th>
							<th>Categoria</th>
							<th>Acciones</th>
						</tr>
						<%
						List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
						for (Producto producto : listaProductos) {
						%>
						<tr>
							<td><%=producto.getId_producto()%></td>
							<td><%=producto.getNombre()%></td>
							<td><%=producto.getDescripcion()%></td>
							<td><%=producto.getPrecio()%></td>
							<td><%=(producto.getId_categoria() == 1) ? "Desayuno"
		: (producto.getId_categoria() == 2) ? "Postres"
				: (producto.getId_categoria() == 3) ? "Mini Dulces" : "Mini Tortas"%></td>
							<td><a class="btn btn-warning"
								href="productos?action=edit&id=<%=producto.getId_producto()%>">Editar</a> </td>
								<td>
								<a class="btn btn-danger"
								onclick="return confirm('¿Está seguro de eliminar este producto?')"
								href="productos?action=delete&id=<%=producto.getId_producto()%>">Eliminar</a>
							</td>
						</tr>
						<%
						}
						%>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp" />
</body>
</html>
