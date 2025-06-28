<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.Producto"%>

<%
Producto producto = (Producto) request.getAttribute("producto");
String action = producto != null ? "Actualizar" : "Agregar";
%>

<html lang="es">
<head>
<jsp:include page="includes/head.jsp" />
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<h1 class="text-center"><%=action%>
		Producto
	</h1>
	<div class="container">
		<div class="row">
			<form action="productos" method="post"  enctype="multipart/form-data">
				<input type="hidden" name="action"
					value="<%=producto != null ? "update" : "create"%>"> <input
					type="hidden" name="id_producto"
					value="<%=producto != null ? producto.getId_producto() : ""%>">
				<div class="input-group flex-nowrap">
					<span class="input-group-text" id="addon-wrapping">Nombre</span> <input
						type="text" name="nombre"
						value="<%=producto != null ? producto.getNombre() : ""%>" required
						class="form-control" aria-describedby="addon-wrapping">
				</div>
				<div class="input-group flex-nowrap">
					<span class="input-group-text" id="addon-wrapping">Descripción</span>
					<textarea name="descripcion" class="form-control"
						style="resize: none;" required><%=producto != null ? producto.getDescripcion() : ""%></textarea>
				</div>
				<div class="input-group flex-nowrap">
					<span class="input-group-text" id="addon-wrapping">Precio</span> <input
						type="number" name="precio" class="form-control"
						value="<%=producto != null ? String.format("%.2f", producto.getPrecio()) : ""%>"
						required step="0.01">
				</div>
				<div>
					<div class="input-group flex-nowrap">
						<span class="input-group-text" id="addon-wrapping">Categoria</span>
						<select class="form-control" name="id_categoria" required>
							<option value="" disabled select>Seleccione una
								categoría</option>
							<option value="1"
								<%=producto != null && producto.getId_categoria() == 1 ? "selected" : ""%>>DESAYUNO</option>
							<option value="2"
								<%=producto != null && producto.getId_categoria() == 2 ? "selected" : ""%>>POSTRES</option>
							<option value="3"
								<%=producto != null && producto.getId_categoria() == 3 ? "selected" : ""%>>MINIDULCES</option>
							<option value="4"
								<%=producto != null && producto.getId_categoria() == 4 ? "selected" : ""%>>MINI
								TORTAS</option>
						</select>
					</div>

				</div>
				<div class="input-group flex-nowrap">
					<span class="input-group-text" id="addon-wrapping">Imagen</span>
					 <input type="file" name="imagen" accept=".png" class="form-control" aria-describedby="addon-wrapping" required>
				</div>
				<div class="my-3">
					<button class="btn btn-success" type="submit"><%=action%></button>
				</div>

			</form>
			<a href="productos?action=productos" class="btn btn-secondary btn-sm">Volver a la lista</a>
		</div>
	</div>

	<jsp:include page="includes/footer.jsp" />
</body>
</html>

