package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Venta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.VentaDAO;

/**
 * Servlet implementation class ventrasController
 */
@WebServlet("/ventas")
public class VentaController extends HttpServlet {
    private VentaDAO ventaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializamos el DAO
        ventaDAO = new VentaDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener todas las ventas
            List<Venta> ventas = ventaDAO.obtenerTodasLasVentas();

            // Establecer las ventas en el request
            request.setAttribute("ventas", ventas);

            // Redirigir a la página JSP
            request.getRequestDispatcher("/WEB-INF/views/ventas.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            // Enviar un mensaje de error en caso de fallo
            request.setAttribute("error", "Ocurrió un error al obtener las ventas.");
            request.getRequestDispatcher("/WEB-INF/views/ventas.jsp").forward(request, response);
        }
    }
}
