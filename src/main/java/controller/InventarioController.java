package controller;

import model.Inventario;
import model.Producto;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/inventario")
public class InventarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String jdbcURL = "jdbc:mysql://localhost:3306/pane";
    private String jdbcUser = "root";
    private String jdbcPass = "root";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userType = (Integer) request.getSession().getAttribute("idTipoUsuario");
        if (userType == null || userType != 1) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null)
            action = "";

        switch (action) {
            case "new":
                mostrarFormulario(request, response, null);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Inventario inventario = obtenerInventarioPorId(idEdit);
                mostrarFormulario(request, response, inventario);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                eliminarInventario(idDelete);
                response.sendRedirect("inventario");
                break;
            default:
                listarInventario(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = request.getParameter("id_inventario").isEmpty() ? 0
                : Integer.parseInt(request.getParameter("id_inventario"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
        String categorizacion = request.getParameter("categorizacion");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);

            if (id == 0) {
                // Verificamos si ya existe un registro para ese producto
                String sqlSelect = "SELECT id_inventario, cantidad FROM inventario WHERE id_producto = ?";
                PreparedStatement psSelect = con.prepareStatement(sqlSelect);
                psSelect.setInt(1, idProducto);
                ResultSet rs = psSelect.executeQuery();

                if (rs.next()) {
                    // Ya existe inventario para ese producto, actualizamos sumando cantidad
                    int idInventarioExistente = rs.getInt("id_inventario");
                    int cantidadExistente = rs.getInt("cantidad");

                    String sqlUpdate = "UPDATE inventario SET cantidad = ?, CategorizacionABC = ? WHERE id_inventario = ?";
                    PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                    psUpdate.setInt(1, cantidadExistente + cantidad);
                    psUpdate.setString(2, categorizacion);
                    psUpdate.setInt(3, idInventarioExistente);
                    psUpdate.executeUpdate();
                    psUpdate.close();
                    id = idInventarioExistente;

                } else {
                    // No existe inventario para ese producto, insertamos nuevo registro
                    ResultSet rse = null;
                    String sqlInsert = "INSERT INTO inventario (cantidad, id_producto, CategorizacionABC) VALUES (?, ?, ?)";
                    PreparedStatement psInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

                    psInsert.setInt(1, cantidad);
                    psInsert.setInt(2, idProducto);
                    psInsert.setString(3, categorizacion);
                    psInsert.executeUpdate();
                    rse = psInsert.getGeneratedKeys();
                    if (rse.next()) {
                        id = rse.getInt(1);
                    }
                    psInsert.close();
                }

                rs.close();
                psSelect.close();

            } else {
                // Actualizar un registro existente (editar)
                String sqlUpdate = "UPDATE inventario SET cantidad=?, id_producto=?, CategorizacionABC=? WHERE id_inventario=?";
                PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, cantidad);
                psUpdate.setInt(2, idProducto);
                psUpdate.setString(3, categorizacion);
                psUpdate.setInt(4, id);
                psUpdate.executeUpdate();
                psUpdate.close();
            }

            // REGISTRAR MOVIMIENTO
            String sqlInsert = "INSERT INTO movimiento_producto (id_inventario, fecha, detalle, entrada, salida) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psInsert = con.prepareStatement(sqlInsert);
            psInsert.setInt(1, id);
            psInsert.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            psInsert.setString(3, "COMPRA");
            psInsert.setInt(4, cantidad);
            psInsert.setInt(5, 0);
            psInsert.executeUpdate();
            psInsert.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("inventario");
    }

    private void listarInventario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Inventario> listaInventario = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
            String sql = "SELECT i.id_inventario, i.cantidad, i.id_producto, i.CategorizacionABC, p.nombre AS nombreProducto "
                    +
                    "FROM inventario i " +
                    "JOIN productos p ON i.id_producto = p.id_producto";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Inventario inv = new Inventario();
                inv.setId_inventario(rs.getInt("id_inventario"));
                inv.setCantidad(rs.getInt("cantidad"));
                inv.setId_producto(rs.getInt("id_producto"));
                inv.setCategorizacionABC(rs.getString("CategorizacionABC"));
                inv.setNombreProducto(rs.getString("nombreProducto")); // Asignamos el nombre
                listaInventario.add(inv);
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("listaInventario", listaInventario);
        request.getRequestDispatcher("/WEB-INF/views/inventario.jsp").forward(request, response);
    }

    private Inventario obtenerInventarioPorId(int id) {
        Inventario inv = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM inventario WHERE id_inventario=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                inv = new Inventario();
                inv.setId_inventario(rs.getInt("id_inventario"));
                inv.setCantidad(rs.getInt("cantidad"));
                inv.setId_producto(rs.getInt("id_producto"));
                inv.setCategorizacionABC(rs.getString("CategorizacionABC"));
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inv;
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Inventario inventario)
            throws ServletException, IOException {
        request.setAttribute("inventario", inventario);

        // Cargar productos
        List<Producto> productos = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_producto, nombre FROM productos");

            while (rs.next()) {
                Producto p = new Producto();
                p.setId_producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                productos.add(p);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/views/inventario-form.jsp").forward(request, response);
    }

    private void eliminarInventario(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
            PreparedStatement ps = con.prepareStatement("DELETE FROM inventario WHERE id_inventario=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
