package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardDAO {
    private Connection conn;

    public DashboardDAO(Connection conn) {
        this.conn = conn;
    }

    public int obtenerTotalClientes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM clientes"; // Asegúrate que sea "cliente" o "clientes"
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int obtenerTotalVentas() throws SQLException {
        String sql = "SELECT COUNT(*) FROM ventas";
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public double obtenerTotalGanancias() throws SQLException {
        String sql = """
                    SELECT SUM(p.precio)
                    FROM detalles_venta dv
                    JOIN productos p ON dv.id_producto = p.id_producto
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        }
    }

    public Map<String, Integer> obtenerVentasPorMes() throws SQLException {
        String sql = """
                    SELECT MONTH(fecha_venta) AS mes, COUNT(*) AS total
                    FROM ventas
                    GROUP BY mes
                    ORDER BY mes
                """;

        Map<String, Integer> ventasPorMes = new LinkedHashMap<>();
        String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int mes = rs.getInt("mes");
                int total = rs.getInt("total");
                ventasPorMes.put(meses[mes - 1], total);
            }
        }

        return ventasPorMes;
    }

    public Map<String, Integer> obtenerInventarioPorCategoria() throws SQLException {
        String sql = """
                    SELECT c.nombre AS categoria, SUM(i.cantidad) AS total
                    FROM inventario i
                    JOIN productos p ON i.id_producto = p.id_producto
                    JOIN categoria c ON p.id_categoria = c.id_categoria
                    GROUP BY c.nombre
                """;

        Map<String, Integer> inventarioPorCategoria = new LinkedHashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String categoria = rs.getString("categoria");
                int cantidad = rs.getInt("total");
                inventarioPorCategoria.put(categoria, cantidad);
            }
        }

        return inventarioPorCategoria;
    }
}
