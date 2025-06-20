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

        String sql = "SELECT * FROM movimiento_producto ORDER BY fecha DESC, id_movimiento DESC";

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

                listaMovimientos.add(mov);
            }
        }

        return listaMovimientos;
    }
}
