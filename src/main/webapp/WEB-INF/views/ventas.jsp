<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.Venta"%>
<%@ page import="java.util.List"%>
<!-- sesion para que no se pueda entrar a la vista sin autorizacion by:clever -->
<%
Integer userType = (Integer) session.getAttribute("idTipoUsuario");
if (userType == null || (userType != 1)) {
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
            <jsp:include page="includes/panelAdmin.jsp"></jsp:include>
            <div class="col-md-9">
                <h2 class="text-center">Listado de Ventas</h2>

                <!-- Mostrar mensaje de error si existe -->
                <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
                %>
                <div class="alert alert-danger">
                    <%=error%>
                </div>
                <%
                }
                %>

                <!-- Tabla de ventas -->
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID Venta</th>
                                <th>ID Cliente</th>
                                <th>Fecha Venta</th>
                                <th>Hora Venta</th>
                                <th>Total</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            List<Venta> ventas = (List<Venta>) request.getAttribute("ventas");
                            if (ventas != null && !ventas.isEmpty()) {
                                for (Venta venta : ventas) {
                            %>
                            <tr>
                                <td><%=venta.getIdVenta()%></td>
                                <td><%=venta.getIdCliente()%></td>
                                <td><%=venta.getFechaVenta()%></td>
                                <td><%=venta.getHoraVenta()%></td>
                                <td><%=venta.getTotal()%></td>
                                <td><%= venta.obtenerEstadoVenta() %></td> <!-- Mostrar el estado -->
                                <td>
                                    <!-- Botón para ver los detalles de la venta -->
                                    <a href="<%=request.getContextPath() + "/verDetallesVenta?idVenta=" + venta.getIdVenta()%>"
                                       class="btn btn-info">Ver Detalles</a>

                                    <!-- Formulario para actualizar el estado -->
                                    <form action="<%=request.getContextPath() + "/actualizarEstadoVenta"%>" method="POST" style="display:inline;">
                                        <input type="hidden" name="idVenta" value="<%=venta.getIdVenta()%>">
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
                            <%
                            }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
