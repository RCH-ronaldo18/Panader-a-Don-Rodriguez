<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Producto" %>
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
</head>

<body>
    <jsp:include page="includes/header.jsp" />

    <h2 class="text-center">Lista de Productos</h2>
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="includes/panelAdmin.jsp" />

            <div class="col-md-9">
                <div class="row mb-3">
                    <div class="col-md-8">
                        <form action="productos" method="get" class="form-inline d-flex gap-3 align-items-center">
                            <label for="categoria" class="mb-0">Filtrar por categoría:</label>
                            <select name="categoria" id="categoria" class="form-control">
                                <option value="">Todas</option>
                                <option value="1">Desayuno</option>
                                <option value="2">Postres</option>
                                <option value="3">Mini Dulces</option>
                                <option value="4">Mini Tortas</option>
                            </select>
                            <button type="submit" class="btn btn-primary">Filtrar</button>
                        </form>
                    </div>

                    <div class="col-md-4 d-flex justify-content-center align-items-center">
                        <a class="btn btn-info" href="productos?action=new">Agregar Producto</a>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark text-center align-middle">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Precio</th>
                                <th>Categoria</th>
                                <th colspan="2">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
                                if (listaProductos != null && !listaProductos.isEmpty()) {
                                    for (Producto producto : listaProductos) {
                            %>
                            <tr class="align-middle">
                                <td class="text-center"><%= producto.getId_producto() %></td>
                                <td class="text-start"><%= producto.getNombre() %></td>
                                <td class="text-start"><%= producto.getDescripcion() %></td>
                                <td class="text-center"><%= producto.getPrecio() %></td>
                                <td class="text-center">
                                    <%= (producto.getId_categoria() == 1) ? "Desayuno"
                                        : (producto.getId_categoria() == 2) ? "Postres"
                                        : (producto.getId_categoria() == 3) ? "Mini Dulces"
                                        : "Mini Tortas" %>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-warning btn-sm" href="productos?action=edit&id=<%= producto.getId_producto() %>">Editar</a>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-danger btn-sm"
                                        onclick="return confirm('¿Está seguro de eliminar este producto?')"
                                        href="productos?action=delete&id=<%= producto.getId_producto() %>">Eliminar</a>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="7" class="text-center">No hay productos registrados.</td>
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
