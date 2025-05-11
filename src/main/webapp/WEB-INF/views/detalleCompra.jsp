<%@ page import="dao.VentaDAO" %>
<%@ page import="model.Venta" %>
<%@ page import="model.DetalleVenta" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
<jsp:include page="includes/head.jsp"/>

<body>
    <header>
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <h2>Detalle de Compra</h2>
                </div>
            </div>
        </div>
    </header>

    <div class="container">
        <% if (venta != null) { %>
            <div class="row">
                <div class="col-12">
                    <h4>Compra ID: <%= venta.getIdVenta() %></h4>
                    <p><strong>Fecha de la Compra:</strong> <%= venta.getFechaVenta() %></p>
                    <p><strong>Hora de la Compra:</strong> <%= venta.getHoraVenta() %></p>
                    <p><strong>Total:</strong> S/<%= venta.getTotal() %></p>
                </div>
            </div>

            <% if (detalles != null && !detalles.isEmpty()) { %>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID Producto</th>
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
                                    <td><%= detalle.getIdProducto() %></td>
                                    <td><%= detalle.getCantidad() %></td>
                                    <td>S/<%= detalle.getPrecioUnitario() %></td>
                                    <td>S/<%= detalle.getSubtotal() %></td>
                                    <td>S/<%= detalle.getIgv() %></td>
                                    <td>S/<%= detalle.getTotal() %></td>
                                </tr>
                            <% 
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <p>No se encontraron detalles para esta compra.</p>
            <% } %>

        <% } else { %>
            <p>No se encontró la venta.</p>
        <% } %>

    </div>

    <jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>

