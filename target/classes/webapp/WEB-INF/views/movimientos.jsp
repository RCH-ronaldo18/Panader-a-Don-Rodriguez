<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

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
    <title>Kardex de Inventario</title>
</head>

<body>
<jsp:include page="includes/header.jsp" />

<div class="container-fluid mt-4">
    <div class="row">
        <jsp:include page="includes/panelAdmin.jsp" />

        <div class="col-md-9">
            <h2 class="text-center mb-4">KARDEX</h2>

            <div class="table-responsive">
                <table class="table table-bordered table-sm text-center">
                    <thead class="table-dark">
                        <tr>
                            <th>Fecha</th>
                            <th>Producto</th>
                            <th>Detalle</th>
                            <th>Entrada</th>
                            <th>Salida</th>
                            <th>Costo Unitario</th>
                            <th>Total Movimiento</th>
                            <th>Existencia (Cant)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Movimiento> listaMovimientos = (List<Movimiento>) request.getAttribute("listaMovimientos");
                            Map<Integer, Integer> existenciasActuales = (Map<Integer, Integer>) request.getAttribute("existenciasActuales");

                            if (listaMovimientos != null && !listaMovimientos.isEmpty()) {
                                for (Movimiento m : listaMovimientos) {
                                    int entrada = m.getEntrada();
                                    int salida = m.getSalida();
                                    double precio = m.getCostoUnitario();
                                    double total = m.getTotalMovimiento();
                                    int stockActual = existenciasActuales.getOrDefault(m.getIdInventario(), 0);
                        %>
                        <tr>
                            <td><%= m.getFecha() %></td>
                            <td><%= m.getNombreProducto() %></td>
                            <td><%= m.getDetalle() %></td>
                            <td><%= entrada > 0 ? entrada : "" %></td>
                            <td><%= salida > 0 ? salida : "" %></td>
                            <td>S/ <%= String.format("%.2f", precio) %></td>
                            <td>S/ <%= String.format("%.2f", total) %></td>
                            <td><%= stockActual %></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr><td colspan="8">No hay movimientos registrados.</td></tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp" />
</body>
</html>
