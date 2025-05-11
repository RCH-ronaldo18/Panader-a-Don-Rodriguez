package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DatabaseConnection;
import model.Inventario;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
@WebServlet("/inventario")
public class InventarioController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listInventario(request, response);
                break;
            case "delete":
                deleteInventario(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            default:
                listInventario(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                createInventario(request, response);
                break;
            case "update":
                updateInventario(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                break;
        }
    }

    // Método para listar inventario
    private void listInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Inventario> listaInventario = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM inventario")) {

            while (resultSet.next()) {
                listaInventario.add(new Inventario(
                        resultSet.getLong("id_inventario"),
                        resultSet.getInt("cantidad"),
                        resultSet.getDouble("precio"),
                        resultSet.getString("fecha_ingreso"),
                        resultSet.getString("hora_ingreso"),
                        resultSet.getString("fecha_salida"),
                        resultSet.getString("hora_salida"),
                        resultSet.getLong("id_producto"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores: podrías redirigir a un error page o mostrar un mensaje en la vista
            request.setAttribute("errorMessage", "Error al listar inventario: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
        request.setAttribute("listaInventario", listaInventario);
        request.getRequestDispatcher("/WEB-INF/views/inventario.jsp").forward(request, response);
    }

    // Método para crear un nuevo inventario
    private void createInventario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            String fecha_ingreso = request.getParameter("fecha_ingreso");
            String hora_ingreso = request.getParameter("hora_ingreso");
            String fecha_salida = request.getParameter("fecha_salida");
            String hora_salida = request.getParameter("hora_salida");
            Long id_producto = Long.parseLong(request.getParameter("id_producto"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            saveInventarioToDatabase(cantidad, precio, fecha_ingreso, hora_ingreso, fecha_salida, hora_salida, id_producto, nombre, descripcion);
            response.sendRedirect("inventario");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos");
        }
    }

    // Método para actualizar un inventario
    private void updateInventario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id_inventario = Long.parseLong(request.getParameter("id_inventario"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            String fecha_ingreso = request.getParameter("fecha_ingreso");
            String hora_ingreso = request.getParameter("hora_ingreso");
            String fecha_salida = request.getParameter("fecha_salida");
            String hora_salida = request.getParameter("hora_salida");
            Long id_producto = Long.parseLong(request.getParameter("id_producto"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            updateInventarioInDatabase(id_inventario, cantidad, precio, fecha_ingreso, hora_ingreso, fecha_salida, hora_salida, id_producto, nombre, descripcion);
            response.sendRedirect("inventario");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos");
        }
    }

    // Método para eliminar un inventario
    private void deleteInventario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id_inventario = Long.parseLong(request.getParameter("id"));
            deleteInventarioFromDatabase(id_inventario);
            response.sendRedirect("inventario");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos");
        }
    }

    // Métodos auxiliares
    private void saveInventarioToDatabase(int cantidad, double precio, String fecha_ingreso, String hora_ingreso, String fecha_salida, String hora_salida, Long id_producto, String nombre, String descripcion) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO inventario (cantidad, precio, fecha_ingreso, hora_ingreso, fecha_salida, hora_salida, id_producto, nombre, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, cantidad);
                statement.setDouble(2, precio);
                statement.setString(3, fecha_ingreso);
                statement.setString(4, hora_ingreso);
                statement.setString(5, fecha_salida);
                statement.setString(6, hora_salida);
                statement.setLong(7, id_producto);
                statement.setString(8, nombre);
                statement.setString(9, descripcion);
                statement.executeUpdate();
            }
        }
    }

    private void updateInventarioInDatabase(Long id_inventario, int cantidad, double precio, String fecha_ingreso, String hora_ingreso, String fecha_salida, String hora_salida, Long id_producto, String nombre, String descripcion) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE inventario SET cantidad = ?, precio = ?, fecha_ingreso = ?, hora_ingreso = ?, fecha_salida = ?, hora_salida = ?, id_producto = ?, nombre = ?, descripcion = ? WHERE id_inventario = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, cantidad);
                statement.setDouble(2, precio);
                statement.setString(3, fecha_ingreso);
                statement.setString(4, hora_ingreso);
                statement.setString(5, fecha_salida);
                statement.setString(6, hora_salida);
                statement.setLong(7, id_producto);
                statement.setString(8, nombre);
                statement.setString(9, descripcion);
                statement.setLong(10, id_inventario);
                statement.executeUpdate();
            }
        }
    }

    private void deleteInventarioFromDatabase(Long id_inventario) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM inventario WHERE id_inventario = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id_inventario);
                statement.executeUpdate();
            }
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id_inventario = Long.parseLong(request.getParameter("id"));
        Inventario inventario = getInventarioById(id_inventario);
        request.setAttribute("inventario", inventario);
        request.getRequestDispatcher("/WEB-INF/views/inventario-form.jsp").forward(request, response);
    }

    private Inventario getInventarioById(long id_inventario) {
        Inventario inventario = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM inventario WHERE id_inventario = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id_inventario);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        inventario = new Inventario(
                                resultSet.getLong("id_inventario"),
                                resultSet.getInt("cantidad"),
                                resultSet.getDouble("precio"),
                                resultSet.getString("fecha_ingreso"),
                                resultSet.getString("hora_ingreso"),
                                resultSet.getString("fecha_salida"),
                                resultSet.getString("hora_salida"),
                                resultSet.getLong("id_producto"),
                                resultSet.getString("nombre"),
                                resultSet.getString("descripcion")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventario;
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/inventario-form.jsp").forward(request, response);
    }
}

