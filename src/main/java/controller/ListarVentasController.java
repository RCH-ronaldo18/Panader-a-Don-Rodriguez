package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.VentaDAO;
import model.Venta;

@WebServlet("/listarVentas")
public class ListarVentasController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VentaDAO ventaDAO;

    @Override
    public void init() {
        ventaDAO = new VentaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Venta> listaVentas = ventaDAO.obtenerTodasLasVentas();
            request.setAttribute("ventas", listaVentas);
            request.getRequestDispatcher("/WEB-INF/views/ventas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener la lista de ventas.");
            request.getRequestDispatcher("/WEB-INF/views/ventas.jsp").forward(request, response);
        }
    }
}
