<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Inventario" %>

<%
    Integer userType = (Integer) session.getAttribute("idTipoUsuario");
    if (userType == null || userType != 1) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    List<Inventario> listaInventario = (List<Inventario>) request.getAttribute("listaInventario");
%>

<html>
<head>
    <jsp:include page="includes/head.jsp" />
</head>

<body>
    <jsp:include page="includes/header.jsp" />
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="includes/panelAdmin.jsp" />
            <div class="col-md-9">
                <h2>Inventario</h2>
                <a href="inventario?action=new" class="btn btn-primary mb-3">Agregar Inventario</a>

                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark text-center align-middle">
                        <tr>
                            <th>ID</th>
                            <th>Cantidad</th>
                            <th>Producto</th>
                            <th>Categorización ABC</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (listaInventario != null && !listaInventario.isEmpty()) {
                            for (Inventario inv : listaInventario) {
                        %>
                        <tr class="align-middle">
                            <td class="text-center"><%= inv.getId_inventario() %></td>
                            <td class="text-center"><%= inv.getCantidad() %></td>
                            <td class="text-start"><%= inv.getNombreProducto() %></td>
                            <td class="text-center">
                                <%= "A".equals(inv.getCategorizacionABC()) ? "A - Más vendido"
                                   : "B".equals(inv.getCategorizacionABC()) ? "B - Moderado"
                                   : "C - Bajo movimiento" %>
                            </td>
                            <td class="text-center">
                                <a href="inventario?action=edit&id=<%= inv.getId_inventario() %>" class="btn btn-warning btn-sm">Editar</a>
                                <a href="inventario?action=delete&id=<%= inv.getId_inventario() %>" class="btn btn-danger btn-sm"
                                   onclick="return confirm('¿Deseas eliminar este registro?')">Eliminar</a>
                            </td>
                        </tr>
                        <%  }
                           } else { %>
                        <tr>
                            <td colspan="5" class="text-center">No hay registros en el inventario.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
    <jsp:include page="includes/footer.jsp" />
</body>
</html>
