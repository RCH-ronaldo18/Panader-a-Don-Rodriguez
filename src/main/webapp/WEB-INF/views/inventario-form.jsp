<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.Inventario"%>

<%
Inventario inventario = (Inventario) request.getAttribute("inventario");
String action = inventario != null ? "Actualizar" : "Agregar";
%>

<html lang="es">
<head>
    <jsp:include page="includes/head.jsp" />
</head>
<body>
    <jsp:include page="includes/header.jsp" />
    <h1 class="text-center"><%= action %> Inventario</h1>
    <div class="container">
        <div class="row">
            <form action="inventario" method="post">
                <input type="hidden" name="action" value="<%= inventario != null ? "update" : "create" %>">
                <input type="hidden" name="id_inventario" value="<%= inventario != null ? inventario.getId_inventario() : "" %>">
                
                <div class="mb-3">
                    <label for="cantidad" class="form-label">Cantidad</label>
                    <input type="number" class="form-control" id="cantidad" name="cantidad" 
                           value="<%= inventario != null ? inventario.getCantidad() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="text" class="form-control" id="precio" name="precio" 
                           value="<%= inventario != null ? String.format("%.2f", inventario.getPrecio()) : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="fechaIngreso" class="form-label">Fecha de Ingreso</label>
                    <input type="date" class="form-control" id="fechaIngreso" name="fechaIngreso" 
                           value="<%= inventario != null ? inventario.getFecha_ingreso() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="horaIngreso" class="form-label">Hora de Ingreso</label>
                    <input type="time" class="form-control" id="horaIngreso" name="horaIngreso" 
                           value="<%= inventario != null ? inventario.getHora_ingreso() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="fechaSalida" class="form-label">Fecha de Salida</label>
                    <input type="date" class="form-control" id="fechaSalida" name="fechaSalida" 
                           value="<%= inventario != null ? inventario.getFecha_salida() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="horaSalida" class="form-label">Hora de Salida</label>
                    <input type="time" class="form-control" id="horaSalida" name="horaSalida" 
                           value="<%= inventario != null ? inventario.getHora_salida() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="idProducto" class="form-label">ID Producto</label>
                    <input type="number" class="form-control" id="idProducto" name="idProducto" 
                           value="<%= inventario != null ? inventario.getId_producto() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" 
                           value="<%= inventario != null ? inventario.getNombre() : "" %>" required>
                </div>

                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%= inventario != null ? inventario.getDescripcion() : "" %></textarea>
                </div>

                <div class="my-3">
                    <button class="btn btn-success" type="submit"><%= action %></button>
                </div>
            </form>
            <a href="inventario" class="btn btn-secondary btn-sm">Volver a la lista</a>
        </div>
    </div>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>


