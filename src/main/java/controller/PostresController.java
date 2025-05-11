package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DatabaseConnection;
import model.Producto;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@WebServlet("/postres")
public class PostresController extends HttpServlet {
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        listProductosPorCategoria(request, response);
	    }

	    private void listProductosPorCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        List<Producto> listaProductos = new ArrayList<>();
	        int id_categoria = 2; // Cambia el id_categoria según la categoría de Postres

	        try (Connection connection = DatabaseConnection.getConnection()) {
	            String sql = "SELECT * FROM productos WHERE id_categoria = ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setInt(1, id_categoria);
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

	        request.setAttribute("listaProductos", listaProductos);
	        request.getRequestDispatcher("/WEB-INF/views/postres.jsp").forward(request, response);
	    }
	}