package controller;

import model.DatabaseConnection;
import model.Movimiento;
import model.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/movimientos")
public class MovimientoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private List<Producto> obtenerProductos() throws SQLException {
        List<Producto> listaProductos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id_producto, nombre, descripcion, precio, id_categoria FROM productos";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getInt("id_categoria"));
                    listaProductos.add(p);
                }
            }
        }
        return listaProductos;
    }

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");

    if ("nuevo".equals(action)) {
        try {
            request.setAttribute("listaProductos", obtenerProductos());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar productos.");
        }
        request.getRequestDispatcher("/WEB-INF/views/movimiento-form.jsp").forward(request, response);
    } else {
        List<Movimiento> listaMovimientos = new ArrayList<>();
        Map<Integer, Integer> existenciasActuales = new java.util.HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Obtener movimientos con datos del producto
            String sql = "SELECT mp.id_movimiento, mp.id_inventario, mp.fecha, mp.detalle, mp.entrada, mp.salida, " +
                         "p.nombre AS nombre_producto, p.precio AS precio_unitario " +
                         "FROM movimiento_producto mp " +
                         "JOIN inventario i ON mp.id_inventario = i.id_inventario " +
                         "JOIN productos p ON i.id_producto = p.id_producto " +
                         "ORDER BY mp.fecha ASC, mp.id_movimiento ASC";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Movimiento m = new Movimiento();
                    m.setIdMovimiento(rs.getInt("id_movimiento"));
                    m.setIdInventario(rs.getInt("id_inventario"));
                    m.setFecha(rs.getDate("fecha"));
                    m.setDetalle(rs.getString("detalle"));
                    m.setEntrada(rs.getInt("entrada"));
                    m.setSalida(rs.getInt("salida"));
                    m.setNombreProducto(rs.getString("nombre_producto"));
                    m.setCostoUnitario(rs.getDouble("precio_unitario"));

                    int cantidad = rs.getInt("entrada") > 0 ? rs.getInt("entrada") : rs.getInt("salida");
                    m.setTotalMovimiento(cantidad * rs.getDouble("precio_unitario"));

                    listaMovimientos.add(m);
                }
            }

            // Obtener existencias actuales por id_inventario
            String sqlStock = "SELECT id_inventario, cantidad FROM inventario";
            try (PreparedStatement stmtStock = conn.prepareStatement(sqlStock);
                 ResultSet rsStock = stmtStock.executeQuery()) {

                while (rsStock.next()) {
                    int idInventario = rsStock.getInt("id_inventario");
                    int cantidadActual = rsStock.getInt("cantidad");
                    existenciasActuales.put(idInventario, cantidadActual);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("listaMovimientos", listaMovimientos);
        request.setAttribute("existenciasActuales", existenciasActuales); // importante
        request.getRequestDispatcher("/WEB-INF/views/movimientos.jsp").forward(request, response);
    }
}



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            int tipoPago = Integer.parseInt(request.getParameter("tipoPago"));
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int idVenta = request.getParameter("idVenta") != null && !request.getParameter("idVenta").isEmpty()
                    ? Integer.parseInt(request.getParameter("idVenta"))
                    : 0;
            String descripcion = request.getParameter("descripcion");

            java.sql.Date fecha = new java.sql.Date(System.currentTimeMillis());
            java.sql.Time hora = new java.sql.Time(System.currentTimeMillis());

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);

                int stockActual = 0;
                String sqlStock = "SELECT cantidad FROM inventario WHERE id_producto = ?";
                try (PreparedStatement stockStmt = conn.prepareStatement(sqlStock)) {
                    stockStmt.setInt(1, idProducto);
                    ResultSet rs = stockStmt.executeQuery();
                    if (rs.next()) {
                        stockActual = rs.getInt("cantidad");
                    } else {
                        request.setAttribute("error", "Producto no encontrado en inventario.");
                        request.setAttribute("listaProductos", obtenerProductos());
                        request.getRequestDispatcher("/WEB-INF/views/movimiento-form.jsp").forward(request, response);
                        return;
                    }
                }

                if (cantidad > stockActual) {
                    request.setAttribute("error", "Stock insuficiente.");
                    request.setAttribute("listaProductos", obtenerProductos());
                    request.getRequestDispatcher("/WEB-INF/views/movimiento-form.jsp").forward(request, response);
                    return;
                }

                // Insertar en movimiento (ahora con id_producto, no id_venta)
                String sqlInsert = "INSERT INTO movimiento (cantidad, id_tipo_pago, fecha, hora, id_venta, descripcion, id_producto) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                    stmt.setInt(1, cantidad);
                    stmt.setInt(2, tipoPago);
                    stmt.setDate(3, fecha);
                    stmt.setTime(4, hora);
                    if (idVenta == 0) {
                        stmt.setNull(5, Types.INTEGER);
                    } else {
                        stmt.setInt(5, idVenta);
                    }
                    stmt.setString(6, descripcion);
                    stmt.setInt(7, idProducto); // NUEVO
                    stmt.executeUpdate();
                }

                // Actualizar inventario
                String sqlUpdate = "UPDATE inventario SET cantidad = cantidad - ? WHERE id_producto = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                    updateStmt.setInt(1, cantidad);
                    updateStmt.setInt(2, idProducto);
                    updateStmt.executeUpdate();
                }

                conn.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar el movimiento.");
            try {
                request.setAttribute("listaProductos", obtenerProductos());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.getRequestDispatcher("/WEB-INF/views/movimiento-form.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("movimientos");
    }
}
