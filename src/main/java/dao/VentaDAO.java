package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseConnection;
import model.DetalleVenta;
import model.Venta;

public class VentaDAO {

	public long registrarVenta(Venta venta) throws SQLException {
	    String sql = "INSERT INTO ventas (id_cliente, fecha_venta, hora_venta, total, id_estado) VALUES (?, ?, ?, ?, ?)";
	    try (Connection con = DatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setLong(1, venta.getIdCliente());
	        ps.setDate(2, java.sql.Date.valueOf(venta.getFechaVenta())); 
	        ps.setTime(3, java.sql.Time.valueOf(venta.getHoraVenta())); 
	        ps.setDouble(4, venta.getTotal());
	        ps.setInt(5, venta.getIdEstado());  // Aquí registras el id_estado

	        ps.executeUpdate();

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getLong(1); // Retornar el ID generado
	            }
	        }
	    }
	    return -1;
	}


    // Registrar cada detalle de la venta
    public void registrarDetalleVenta(DetalleVenta detalle, long idVenta) throws SQLException {
        String sql = "INSERT INTO detalles_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal, igv, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idVenta);
            ps.setLong(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setDouble(6, detalle.getIgv());
            ps.setDouble(7, detalle.getTotal());

            ps.executeUpdate();
        }
    }

    // Actualizar el inventario reduciendo el stock
    public boolean actualizarInventario(long idProducto, int cantidadVendida) throws SQLException {
        String sql = "UPDATE inventario SET cantidad = cantidad - ? WHERE id_producto = ? AND cantidad >= ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidadVendida);
            ps.setLong(2, idProducto);
            ps.setInt(3, cantidadVendida); // Verificar que hay stock suficiente

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas == 0) {
                // No hay suficiente stock
                return false;  // Retorna 'false' si no se actualizó el inventario
            }

            return true;  // Retorna 'true' si se actualizó correctamente
        }
    }
 // Obtener la cantidad disponible de un producto
    public int obtenerCantidadDisponible(long idProducto) throws SQLException {
        String sql = "SELECT cantidad FROM inventario WHERE id_producto = ?";
        int cantidadDisponible = 0;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cantidadDisponible = rs.getInt("cantidad");
                }
            }
        }
        return cantidadDisponible;
    }

    // Registrar la venta con sus detalles y actualizar el inventario
    public boolean registrarVentaConDetalles(Venta venta, List<DetalleVenta> detalles) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false); // Iniciar transacción

            try {
                long idVenta = registrarVenta(venta); // Registrar la venta y obtener su ID
                for (DetalleVenta detalle : detalles) {
                    registrarDetalleVenta(detalle, idVenta); // Registrar cada detalle

                    // Intentar actualizar el inventario
                    boolean inventarioActualizado = actualizarInventario(detalle.getIdProducto(), detalle.getCantidad());
                    if (!inventarioActualizado) {
                        throw new SQLException("No hay suficiente stock para el producto : ");
                    }
                }

                con.commit(); // Confirmar la transacción
                return true; // Venta exitosa
            } catch (SQLException ex) {
                con.rollback(); // Revertir si ocurre un error
                throw new SQLException("Error al registrar la venta y los detalles: " + ex.getMessage(), ex);
            }
        }
    }


    // Obtener ventas por cliente
    public List<Venta> obtenerVentasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE id_cliente = ?";
        List<Venta> ventas = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
System.out.println(idCliente);
            ps.setLong(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getLong("id_venta"));
                    venta.setIdCliente(rs.getLong("id_cliente"));
                    venta.setFechaVenta(rs.getDate("fecha_venta").toLocalDate());
                    venta.setHoraVenta(rs.getTime("hora_venta").toLocalTime());
                    venta.setTotal(rs.getDouble("total"));
                    ventas.add(venta);
                }
            }
        }
        return ventas;
    }

    // Obtener venta por ID
    public Venta obtenerVentaPorId(long idVenta) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";
        Venta venta = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    venta = new Venta();
                    venta.setIdVenta(rs.getLong("id_venta"));
                    venta.setIdCliente(rs.getLong("id_cliente"));
                    venta.setFechaVenta(rs.getDate("fecha_venta").toLocalDate());
                    venta.setHoraVenta(rs.getTime("hora_venta").toLocalTime());
                    venta.setTotal(rs.getDouble("total"));
                }
            }
        }
        return venta; 
    }

    // Obtener detalles de una venta por su ID
    public List<DetalleVenta> obtenerDetallesVenta(long idVenta) throws SQLException {
        String sql = """
            SELECT 
                dv.id_producto, 
                p.nombre AS nombre_producto, 
                dv.cantidad, 
                dv.precio_unitario, 
                dv.subtotal, 
                dv.igv, 
                dv.total,
                c.nombre AS nombre_cliente -- Agregamos el nombre del cliente
            FROM 
                detalles_venta dv
            INNER JOIN 
                productos p ON dv.id_producto = p.id_producto
            INNER JOIN 
                ventas v ON dv.id_venta = v.id_venta -- Relación con la tabla ventas
            INNER JOIN 
                clientes c ON v.id_cliente = c.id_cliente -- Relación con la tabla clientes
            WHERE 
                dv.id_venta = ?;
        """;

        List<DetalleVenta> detalles = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setIdProducto(rs.getLong("id_producto"));
                    detalle.setNombreProducto(rs.getString("nombre_producto"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    detalle.setIgv(rs.getDouble("igv"));
                    detalle.setTotal(rs.getDouble("total"));
                    detalle.setNombreCliente(rs.getString("nombre_cliente")); // Guardar nombre del cliente
                    detalles.add(detalle);
                }
            }
        }
        return detalles;
    }

 // Obtener todas las ventas
public List<Venta> obtenerTodasLasVentas() throws SQLException {
    String sql = """
        SELECT 
            v.*, 
            c.nombre AS nombre_cliente
        FROM 
            ventas v
        JOIN 
            clientes c ON v.id_cliente = c.id_cliente
    """;

    List<Venta> ventas = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getLong("id_venta"));
                venta.setIdCliente(rs.getLong("id_cliente"));
                venta.setFechaVenta(rs.getDate("fecha_venta").toLocalDate());
                venta.setHoraVenta(rs.getTime("hora_venta").toLocalTime());
                venta.setTotal(rs.getDouble("total"));
                venta.setIdEstado(rs.getInt("id_estado"));
                venta.setNombreCliente(rs.getString("nombre_cliente")); // Aquí sí funcionará
                ventas.add(venta);
            }
        }
    }
    return ventas;
}

    //ACTUALIZAR ESTADO DE LA VENTA :
    public boolean actualizarEstado(long idVenta, int nuevoEstado) throws SQLException {
        
        String sql = "UPDATE ventas SET id_estado = ? WHERE id_venta = ?";
        
     

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setLong(2, idVenta);
            System.out.println("hh");


            int filasAfectadas = ps.executeUpdate();

               System.out.println("estoy en el dao");
            return filasAfectadas > 0;
        }
    }


    public static int contarVentas() {
        int total = 0;
        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM ventas");
            ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}

  