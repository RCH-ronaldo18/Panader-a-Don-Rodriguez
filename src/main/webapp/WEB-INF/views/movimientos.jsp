<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Movimiento" %>
<%@ page import="java.util.List" %>

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
    <title>Movimientos de Inventario</title>
</head>

<body>
    <jsp:include page="includes/header.jsp" />

    <div class="container-fluid mt-4">
        <div class="row">
            <jsp:include page="includes/panelAdmin.jsp" />

            <div class="col-md-9">
                <h2 class="text-center mb-4">Historial de Movimientos de Inventario</h2>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark text-center">
                            <tr>
                                <th>ID Movimiento</th>
                                <th>ID Inventario</th>
                                <th>Fecha</th>
                                <th>Detalle</th>
                                <th>Entrada</th>
                                <th>Salida</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Movimiento> listaMovimientos = (List<Movimiento>) request.getAttribute("listaMovimientos");
                                if (listaMovimientos != null && !listaMovimientos.isEmpty()) {
                                    for (Movimiento m : listaMovimientos) {
                            %>
                                <tr>
                                    <td><%= m.getIdMovimiento() %></td>
                                    <td><%= m.getIdInventario() %></td>
                                    <td><%= m.getFecha() %></td>
                                    <td><%= m.getDetalle() %></td>
                                    <td><%= m.getEntrada() %></td>
                                    <td><%= m.getSalida() %></td>
                                </tr>
                            <%
                                    }
                                } else {
                            %>
                                <tr>
                                    <td colspan="6" class="text-center">No hay movimientos registrados.</td>
                                </tr>
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
