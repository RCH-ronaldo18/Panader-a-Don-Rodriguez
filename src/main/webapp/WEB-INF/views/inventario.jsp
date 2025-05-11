<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Inventario" %>
<html>
<head>
    <title>Lista de Inventarios</title>
    <link rel="stylesheet" href="path/to/bootstrap.min.css">
    <script src="path/to/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container">
    <h1>Lista de Inventarios</h1>
    <a href="inventario?action=new" class="btn btn-primary">Nuevo Inventario</a>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Cantidad</th>
                <th>Precio</th>
                <th>Fecha de Ingreso</th>
                <th>Hora de Ingreso</th>
                <th>Fecha de Salida</th>
                <th>Hora de Salida</th>
                <th>ID Producto</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Obtener la lista de inventarios del atributo de la solicitud
                List<Inventario> listaInventario = (List<Inventario>) request.getAttribute("listaInventario");
                
                // Verificar si la lista no es nula y tiene elementos
                if (listaInventario != null && !listaInventario.isEmpty()) {
                    for (Inventario inventario : listaInventario) {
            %>
                <tr>
                    <td><%= inventario.getId_inventario() %></td>
                    <td><%= inventario.getCantidad() %></td>
                    <td><%= inventario.getPrecio() %></td>
                    <td><%= inventario.getFecha_ingreso() %></td>
                    <td><%= inventario.getHora_ingreso() %></td>
                    <td><%= inventario.getFecha_salida() %></td>
                    <td><%= inventario.getHora_salida() %></td>
                    <td><%= inventario.getId_producto() %></td>
                    <td><%= inventario.getNombre() %></td>
                    <td><%= inventario.getDescripcion() %></td>
                    <td>
                        <a href="inventario?action=edit&id=<%= inventario.getId_inventario() %>" class="btn btn-warning">Editar</a>
                        <form action="inventario?action=delete" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= inventario.getId_inventario() %>"/>
                            <button type="submit" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este inventario?');">Eliminar</button>
                        </form>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="11" class="text-center">No hay inventarios disponibles.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>
</body>
</html>

