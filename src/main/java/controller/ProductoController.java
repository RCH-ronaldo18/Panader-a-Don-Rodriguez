package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.DatabaseConnection;
import model.Producto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@MultipartConfig
@WebServlet("/productos")
public class ProductoController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listProductos(request, response);
                break;
            case "delete":
                deleteProducto(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            default:
                listProductos(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                createProducto(request, response);
                break;
            case "update":
                updateProducto(request, response);
                break;
            default:
                break;
        }
    }
    private void listProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Producto> listaProductos = new ArrayList<>();
        String categoriaParam = request.getParameter("categoria");  // Obtener el parámetro de categoría
        String sql = "SELECT * FROM productos";  // Consulta base para obtener todos los productos

        if (categoriaParam != null && !categoriaParam.isEmpty()) {
            // Si se seleccionó una categoría, filtrar productos por categoría
            sql += " WHERE id_categoria = ?";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            if (categoriaParam != null && !categoriaParam.isEmpty()) {
                int categoriaId = Integer.parseInt(categoriaParam);
                statement.setInt(1, categoriaId);  // Establecer el valor del parámetro en la consulta
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Producto producto = new Producto(
                        resultSet.getInt("id_producto"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("id_categoria")
                );
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pasar la lista de productos filtrados a la vista
        request.setAttribute("listaProductos", listaProductos);
        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }

private void createProducto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String nombre = request.getParameter("nombre");
    String descripcion = request.getParameter("descripcion");
    double precio = Double.parseDouble(request.getParameter("precio"));
    int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));

    int id_producto = 0;

    try (Connection connection = DatabaseConnection.getConnection()) {
        // Insertar producto
        String sql = "INSERT INTO productos (nombre, descripcion, precio, id_categoria) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setDouble(3, precio);
        statement.setInt(4, id_categoria);
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            id_producto = generatedKeys.getInt(1);
        }

        // Insertar registro en inventario con cantidad 0 y categorización 'C'
        String sqlInventario = "INSERT INTO inventario (cantidad, id_producto, CategorizacionABC) VALUES (0, ?, 'C')";
        PreparedStatement psInv = connection.prepareStatement(sqlInventario);
        psInv.setInt(1, id_producto);
        psInv.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Guardar imagen con el nombre del producto id
    Part filePart = request.getPart("imagen");
    if (filePart != null && filePart.getSize() > 0) {
        String fileName = id_producto + ".png";
        String uploadPath = getServletContext().getRealPath("") + "img/PRODUCTOS/" + fileName;
        filePart.write(uploadPath);
    }

    response.sendRedirect("productos");
}



    private void deleteProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id_producto = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM productos WHERE id_producto = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_producto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("productos");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_producto = Integer.parseInt(request.getParameter("id"));
        Producto producto = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM productos WHERE id_producto = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_producto);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                producto = new Producto(
                        resultSet.getInt("id_producto"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("id_categoria")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("producto", producto);
        request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
    }

    private void updateProducto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id_producto = Integer.parseInt(request.getParameter("id_producto"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));

        // Actualizar los datos del producto en la base de datos
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, id_categoria = ? WHERE id_producto = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, id_categoria);
            statement.setInt(5, id_producto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Manejar la actualización de la imagen
        Part filePart = request.getPart("imagen");
        if (filePart != null && filePart.getSize() > 0) {
            // Guardar la nueva imagen con el id_producto
            String fileName = id_producto + ".png";
            String uploadPath = getServletContext().getRealPath("") + "img/PRODUCTOS/" + fileName;

            // Reemplazar la imagen existente con la nueva
            filePart.write(uploadPath);
        }

        // Redireccionar a la lista de productos
        response.sendRedirect("productos");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
    }
}
