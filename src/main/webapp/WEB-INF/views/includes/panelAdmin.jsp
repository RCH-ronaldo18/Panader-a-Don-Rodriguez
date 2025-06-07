<%
    Integer userType = (Integer) session.getAttribute("idTipoUsuario");
%>
<div class="col-md-3 mt-2">
    <nav class="sidebar d-flex flex-column p-3">
        <h4 class="text-black text-center">
            <%
                if (userType != null) {
                    if (userType == 1 || userType == 2) {
                        out.print("ADMINISTRADOR");
                    } else if (userType == 3) {
                        out.print("CLIENTE");
                    } else {
                        out.print("DESCONOCIDO");
                    }
                } else {
                    out.print("COMPRAS REALIZADAS");
                }
            %>
        </h4>

        <ul class="nav flex-column">
            <% if (userType != null && userType == 1) { %>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/dashboard" class="nav-link">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/productos">Productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/ventas">Ordenes</a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/reporte-ventas" class="nav-link">Reporte Ventas (Excel)</a>
                </li>
            <% } else if (userType != null && userType == 3) { %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/verCompras">Mis Compras</a>
                </li>
            <% } %>
        </ul>
    </nav>
</div>
