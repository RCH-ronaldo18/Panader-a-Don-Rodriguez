package dao;

import java.sql.*;

import model.DatabaseConnection;
import model.TipoUsuario;

public class TipoUsuarioDAO {
    public int registrarTipoUsuario(TipoUsuario tipoUsuario) {
        String sql = "INSERT INTO tipos_usuario (nombre_tipo) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tipoUsuario.getNombre_tipo());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
