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
 * Servlet implementation class VerComprasController
 */
@WebServlet("/verCompras")
public class VerComprasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idCliente = (int) request.getSession().getAttribute("idUsuario");
		VentaDAO ventaDAO = new VentaDAO();

		try {
			// Obtener todas las ventas del cliente
			List<Venta> ventas = ventaDAO.obtenerVentasPorCliente(idCliente);
			request.setAttribute("ventas", ventas);

			// Mostrar la página con las compras
			request.getRequestDispatcher("/WEB-INF/views/verCompras.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}
}
