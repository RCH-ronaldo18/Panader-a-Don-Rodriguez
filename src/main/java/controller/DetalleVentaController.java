package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DetalleVenta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.VentaDAO;

/** 	
 * Servlet implementation class DetalleVentaController
 */
@WebServlet("/detalleVenta")
public class DetalleVentaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DetalleVentaController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idVenta = Long.parseLong(request.getParameter("idVenta"));
        VentaDAO ventaDAO = new VentaDAO();
        try {
            // Obtener detalles de la venta
            List<DetalleVenta> detalles = ventaDAO.obtenerDetallesVenta(idVenta);

            // Pasar los detalles a la vista
            request.setAttribute("detalles", detalles);
            request.getRequestDispatcher("/WEB-INF/views/detallesVenta.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los detalles de la venta: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}

