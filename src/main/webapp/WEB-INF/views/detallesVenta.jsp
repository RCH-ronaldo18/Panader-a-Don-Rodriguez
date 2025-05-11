<%@ page import="dao.VentaDAO"%>
<%@ page import="model.Venta"%>
<%@ page import="model.DetalleVenta"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>

<!-- sesion para que no se pueda entrar a la vista sin autorizacion by:clever -->
<%
Integer userType = (Integer) session.getAttribute("idTipoUsuario");
if (userType == null || userType != 1) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>

<%
// Obtener el idVenta desde la URL
long idVenta = Long.parseLong(request.getParameter("idVenta"));

// Crear instancia de VentaDAO
VentaDAO ventaDAO = new VentaDAO();

// Obtener la venta por idVenta
Venta venta = ventaDAO.obtenerVentaPorId(idVenta);

// Obtener los detalles de la venta
List<DetalleVenta> detalles = ventaDAO.obtenerDetallesVenta(idVenta);
%>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body>
	<jsp:include page="includes/header.jsp" />

	<div class="container-fluid mt-4">
		<div class="row">
			<jsp:include page="includes/panelAdmin.jsp"></jsp:include>
			<div class="col-md-9">
				<h2 class="text-center">Detalle de Compra</h2>

				<%
				if (venta != null) {
				%>
				<div class="row">
					<div class="col-12">
						<h4>
							ID compra:
							<%=venta.getIdVenta()%></h4>
						<p>
							<strong>Fecha de la Compra:</strong>
							<%=venta.getFechaVenta()%></p>
						<p>
							<strong>Hora de la Compra:</strong>
							<%=venta.getHoraVenta()%></p>
						<p>
							<strong>Total:</strong> S/<%=venta.getTotal()%>
						</p>
						<p>
    <strong>Cliente:</strong> <%= detalles.isEmpty() ? "N/A" : detalles.get(0).getNombreCliente() %>
</p>
						
					</div>
				</div>

				<%
				if (detalles != null && !detalles.isEmpty()) {
				%>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Producto</th>
								<th>Cantidad</th>
								<th>Precio Unitario</th>
								<th>Subtotal</th>
								<th>IGV</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (DetalleVenta detalle : detalles) {
							%>
							<tr>
								<td><%=detalle.getNombreProducto()%></td>
								<td><%=detalle.getCantidad()%></td>
								<td>S/<%=detalle.getPrecioUnitario()%></td>
								<td>S/<%=detalle.getSubtotal()%></td>
								<td>S/<%=detalle.getIgv()%></td>
								<td>S/<%=detalle.getTotal()%></td>
								
							</tr>

							<%
							}
							%>
						</tbody>
					</table>
				</div>
				<%
				} else {
				%>
				<p class="text-center">No se encontraron detalles para esta
					compra.</p>
				<%
				}
				%>

				<%
				} else {
				%>
				<p class="text-center">No se encontró la venta.</p>
				<%
				}
				%>
		<div class="text-end">
						<a href="${pageContext.request.contextPath}/ventas" class="btn btn-info text-black">Regresar a la lista</a>
		</div>
			</div>
		</div>
	</div>

	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>
