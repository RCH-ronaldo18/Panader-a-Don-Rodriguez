package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.DatabaseConnection;
import model.Usuario;
import model.UsuarioCliente;

public class UsuarioAdminDAO {

    // Método para registrar un usuario y devolver su ID
    public int registrarUsuarioYRetornarId(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (correo_usuario, contrasena, id_tipo_usuario) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getCorreo_usuario());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getId_tipo_usuario());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Método para listar todos los usuarios
    public List<UsuarioCliente> listarUsuariosConClientes() throws SQLException {
        List<UsuarioCliente> lista = new ArrayList<>();
        String sql = """
            SELECT u.id_usuario, u.correo_usuario, u.contrasena, u.id_tipo_usuario,
                   c.id_cliente, c.nombre, c.direccion, c.telefono, c.id_usuario AS cliente_id_usuario
            FROM usuarios u
            LEFT JOIN clientes c ON u.id_usuario = c.id_usuario
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Crear el objeto Usuario
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("correo_usuario"),
                    rs.getString("contrasena"),
                    rs.getInt("id_tipo_usuario")
                );

                // Crear el objeto Cliente (si existen datos)
                Cliente cliente = null;
                if (rs.getInt("id_cliente") != 0) {
                    cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getInt("cliente_id_usuario")
                    );
                }

                // Agregar el UsuarioCliente a la lista
                lista.add(new UsuarioCliente(usuario, cliente));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


 // Método para obtener el cliente asociado a un usuario por su ID
    public Cliente obtenerClientePorIdUsuario(int idUsuario) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id_usuario = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);  // Establecer el ID del usuario como parámetro

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getInt("id_usuario")  // Este campo puede estar en la tabla de clientes si es necesario
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el cliente por ID de usuario", e);
        }
        return cliente;
    }

 // Método para obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("correo_usuario"),
                        rs.getString("contrasena"), // Contraseña recuperada
                        rs.getInt("id_tipo_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

 // Método para actualizar un usuario y su cliente asociado
    public boolean actualizarUsuarioYCliente(Usuario usuario, Cliente cliente) throws SQLException {
        String sqlUsuario = "UPDATE usuarios SET correo_usuario = ?, contrasena = ?, id_tipo_usuario = ? WHERE id_usuario = ?";
        String sqlCliente = "UPDATE clientes SET nombre = ?, direccion = ?, telefono = ? WHERE id_usuario = ?";
        
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false); // Iniciar transacción
            
            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                 PreparedStatement psCliente = con.prepareStatement(sqlCliente)) {

                // Actualizar tabla usuarios
                psUsuario.setString(1, usuario.getCorreo_usuario());
                psUsuario.setString(2, usuario.getContrasena());
                psUsuario.setInt(3, usuario.getId_tipo_usuario());
                psUsuario.setInt(4, usuario.getId_usuario());
                psUsuario.executeUpdate();

                // Actualizar tabla clientes
                psCliente.setString(1, cliente.getNombre());
                psCliente.setString(2, cliente.getDireccion());
                psCliente.setString(3, cliente.getTelefono());
                psCliente.setInt(4, cliente.getIdUsuario());
                psCliente.executeUpdate();

                con.commit(); // Confirmar transacción
                return true;
            } catch (SQLException e) {
                con.rollback(); // Revertir cambios si hay error
                e.printStackTrace();
                return false;
            }
        }
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        String sqlEliminarCliente = "DELETE FROM clientes WHERE id_usuario = ?";
        String sqlEliminarUsuario = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement psCliente = con.prepareStatement(sqlEliminarCliente);
                 PreparedStatement psUsuario = con.prepareStatement(sqlEliminarUsuario)) {

                // Eliminar el cliente asociado
                psCliente.setInt(1, idUsuario);
                psCliente.executeUpdate();

                // Eliminar el usuario
                psUsuario.setInt(1, idUsuario);
                int filasAfectadas = psUsuario.executeUpdate();

                con.commit(); // Confirmar transacción
                return filasAfectadas > 0;
            } catch (SQLException e) {
                con.rollback(); // Revertir cambios si hay error
                e.printStackTrace();
                return false;
            }
        }
    }


    // Método para validar el login
    public Usuario validarUsuario(String correoUsuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo_usuario = ? AND contrasena = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correoUsuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("correo_usuario"),
                        rs.getString("contrasena"),
                        rs.getInt("id_tipo_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public boolean existeUsuarioConOtroId(String correo, int idUsuario) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo_usuario = ? AND id_usuario != ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, correo);
            preparedStatement.setInt(2, idUsuario);
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

