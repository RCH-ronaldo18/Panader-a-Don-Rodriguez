package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseConnection;
import model.Usuario;

public class UsuarioDAO {

    // Método para registrar un usuario y devolver su ID
    public int registrarUsuarioYRetornarId(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (correo_usuario, contrasena, id_tipo_usuario) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Asumimos que la contraseña ya está encriptada en el controlador
            ps.setString(1, usuario.getCorreo_usuario());
            ps.setString(2, usuario.getContrasena()); // Contraseña encriptada
            ps.setInt(3, 3); // Por defecto, id_tipo_usuario = 3 (Usuario)

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Retornar el ID generado
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir el error para facilitar la depuración
        }
        return -1; // Retorna -1 si ocurre algún error o no se genera un ID
    }

    // Método para validar el login
    public Usuario validarUsuario(String correoUsuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo_usuario = ? AND contrasena = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correoUsuario);
            ps.setString(2, contrasena); // Contraseña encriptada para comparar

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Crear un objeto Usuario y devolverlo
                    return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("correo_usuario"),
                        rs.getString("contrasena"),
                        rs.getInt("id_tipo_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir el error para facilitar la depuración
        }
        return null; // Si no se encuentra un usuario, retorna null
    }

    // Validar si el usuario ya existe
    public boolean existeUsuario(String correo) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo_usuario = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, correo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                existe = resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }
}
