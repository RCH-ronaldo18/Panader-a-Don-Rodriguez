package dao;

import model.Movimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/pane";
    private String jdbcUser = "root";
    private String jdbcPass = "root";

    public List<Movimiento> obtenerTodosLosMovimientos() throws SQLException {
        List<Movimiento> listaMovimientos = new ArrayList<>();

        String sql = "SELECT mp.id_movimiento, mp.id_inventario, mp.fecha, mp.detalle, mp.entrada, mp.salida, " +
                     "p.nombre AS nombre_producto, p.precio AS costo_unitario, i.cantidad AS existencia_actual " +
                     "FROM movimiento_producto mp " +
                     "JOIN inventario i ON mp.id_inventario = i.id_inventario " +
                     "JOIN productos p ON i.id_producto = p.id_producto " +
                     "ORDER BY mp.fecha ASC, mp.id_movimiento ASC";

        try (Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Movimiento mov = new Movimiento();
                mov.setIdMovimiento(rs.getInt("id_movimiento"));
                mov.setIdInventario(rs.getInt("id_inventario"));
                mov.setFecha(rs.getDate("fecha"));
                mov.setDetalle(rs.getString("detalle"));
                mov.setEntrada(rs.getInt("entrada"));
                mov.setSalida(rs.getInt("salida"));

                mov.setNombreProducto(rs.getString("nombre_producto"));
                mov.setCostoUnitario(rs.getDouble("costo_unitario"));

                int cantidad = rs.getInt("entrada") > 0 ? rs.getInt("entrada") : rs.getInt("salida");
                double total = cantidad * rs.getDouble("costo_unitario");
                mov.setTotalMovimiento(total);

                mov.setExistenciaActual(rs.getInt("existencia_actual"));

                listaMovimientos.add(mov);
            }
        }

        return listaMovimientos;
    }
}
