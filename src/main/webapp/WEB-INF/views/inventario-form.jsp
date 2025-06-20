<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Inventario" %>
<%@ page import="model.Producto" %>
<%@ page import="java.util.List" %>

<%
Integer userType = (Integer) session.getAttribute("idTipoUsuario");
if (userType == null || userType != 1) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
Inventario inv = (Inventario) request.getAttribute("inventario");
List<Producto> productos = (List<Producto>) request.getAttribute("productos");
%>

<html>
<head>
    <jsp:include page="includes/head.jsp" />
</head>
<body>
<jsp:include page="includes/header.jsp" />
<div class="container">
    <h2><%= (inv != null) ? "Editar Inventario" : "Nuevo Inventario" %></h2>
    <form action="inventario" method="post">
        <input type="hidden" name="id_inventario" value="<%= (inv != null) ? inv.getId_inventario() : "" %>">

        <div class="mb-3">
            <label>Cantidad:</label>
            <input type="number" name="cantidad" class="form-control"
                   value="<%= (inv != null) ? inv.getCantidad() : "" %>" required>
        </div>

        <div class="mb-3">
            <label>Producto:</label>
            <select name="id_producto" class="form-control" required>
                <% for (Producto p : productos) { %>
                    <option value="<%= p.getId_producto() %>"
                        <%= (inv != null && inv.getId_producto() == p.getId_producto()) ? "selected" : "" %>>
                        <%= p.getNombre() %>
                    </option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label>Categorización ABC:</label>
            <select name="categorizacion" class="form-control" required>
                <option value="A" <%= (inv != null && "A".equals(inv.getCategorizacionABC())) ? "selected" : "" %>>A - Más vendido</option>
                <option value="B" <%= (inv != null && "B".equals(inv.getCategorizacionABC())) ? "selected" : "" %>>B - Moderado</option>
                <option value="C" <%= (inv != null && "C".equals(inv.getCategorizacionABC())) ? "selected" : "" %>>C - Bajo movimiento</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Guardar</button>
        <a href="inventario" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
<jsp:include page="includes/footer.jsp" />
</body>
</html>
