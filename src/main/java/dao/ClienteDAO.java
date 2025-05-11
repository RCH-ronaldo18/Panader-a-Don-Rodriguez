package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.DatabaseConnection;

public class ClienteDAO {

    // Método para registrar un cliente en la base de datos
    public boolean registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, direccion, telefono, id_usuario) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDireccion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getIdUsuario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace(); // Loguear el error
            return false; // Retorna false si hubo un error
        }
    }

}
