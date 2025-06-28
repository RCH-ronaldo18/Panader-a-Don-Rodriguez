<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Venta" %>
<%@ page import="java.util.List" %>
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

    <div class="container-fluid mt-4">
        <div class="row">
            <jsp:include page="includes/panelAdmin.jsp" />
            <div class="col-md-9">
                <h2 class="text-center mb-3">Listado de Ventas</h2>

                <% String error = (String) request.getAttribute("error");
                   if (error != null) { %>
                    <div class="alert alert-danger"><%= error %></div>
                <% } %>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark text-center align-middle">
                            <tr>
                                <th scope="col">ID Venta</th>
                                <th scope="col">ID Cliente</th>
                                <th scope="col">Fecha Venta</th>
                                <th scope="col">Hora Venta</th>
                                <th scope="col">Total</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Venta> ventas = (List<Venta>) request.getAttribute("ventas");
                                if (ventas != null && !ventas.isEmpty()) {
                                    for (Venta venta : ventas) {
                            %>
                            <tr class="align-middle">
                                <td class="text-center"><%= venta.getIdVenta() %></td>
                                <td class="text-center"><%= venta.getIdCliente() %></td>
                                <td class="text-center"><%= venta.getFechaVenta() %></td>
                                <td class="text-center"><%= venta.getHoraVenta() %></td>
                                <td class="text-center"><%= venta.getTotal() %></td>
                                <td class="text-center"><%= venta.obtenerEstadoVenta() %></td>
                                <td class="text-center">
                                    <a href="<%= request.getContextPath() + "/verDetallesVenta?idVenta=" + venta.getIdVenta() %>" class="btn btn-info btn-sm">Ver Detalles</a>

                                    <form action="<%= request.getContextPath() + "/actualizarEstadoVenta" %>" method="POST" style="display:inline;">
                                        <input type="hidden" name="idVenta" value="<%= venta.getIdVenta() %>">
                                        <select name="nuevoEstado" class="form-control-sm">
                                            <option value="1" <%= venta.getIdEstado() == 1 ? "selected" : "" %>>Por Atender</option>
                                            <option value="2" <%= venta.getIdEstado() == 2 ? "selected" : "" %>>Completada</option>
                                            <option value="3" <%= venta.getIdEstado() == 3 ? "selected" : "" %>>Atendido</option>
                                        </select>
                                        <button type="submit" class="btn btn-warning btn-sm">Actualizar Estado</button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="7" class="text-center">No hay ventas registradas.</td>
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
