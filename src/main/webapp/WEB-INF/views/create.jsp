<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Venta</title>
</head>
<body>
<h2>Registrar Venta</h2>
<form action="ventas" method="post">
    <input type="hidden" name="action" value="store">
    <label>ID Cliente:</label>
    <input type="text" name="id_cliente" required><br>

    <label>Fecha Venta:</label>
    <input type="date" name="fecha_venta" required><br>

    <label>Hora Venta:</label>
    <input type="time" name="hora_venta" required><br>

    <h3>Detalles de la Venta:</h3>
    <div id="detalles">
        <div>
            <label>Producto:</label>
            <select name="id_producto_0" required>
                <option value="1">Desayuno</option>
                <option value="2">Postres</option>
                <option value="3">Mini Dulces</option>
                <option value="4">Mini Tortas</option>
            </select>
            <label>Cantidad:</label>
            <input type="number" name="cantidad_0" required>
            <label>Precio Unitario:</label>
            <input type="text" name="precio_unitario_0" required>
        </div>
    </div>
    <input type="hidden" name="num_detalles" value="1">
    <button type="button" onclick="addDetalle()">Agregar Detalle</button><br>
    
    <input type="submit" value="Registrar Venta">
</form>

<script>
    let numDetalles = 1;

    function addDetalle() {
        const detallesDiv = document.getElementById("detalles");
        const newDetalle = document.createElement("div");
        newDetalle.innerHTML = `
            <label>Producto:</label>
            <select name="id_producto_${numDetalles}" required>
                <option value="1">Desayuno</option>
                <option value="2">Postres</option>
                <option value="3">Mini Dulces</option>
                <option value="4">Mini Tortas</option>
            </select>
            <label>Cantidad:</label>
            <input type="number" name="cantidad_${numDetalles}" required>
            <label>Precio Unitario:</label>
            <input type="text" name="precio_unitario_${numDetalles}" required>
        `;
        detallesDiv.appendChild(newDetalle);
        numDetalles++;
        document.querySelector('input[name="num_detalles"]').value = numDetalles;
    }
</script>
</body>
</html>

