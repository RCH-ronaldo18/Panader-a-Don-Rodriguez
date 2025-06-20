package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.VentaDAO;

@WebServlet("/actualizarEstadoVenta")
public class ActualizarEstadoVentaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VentaDAO ventaDAO;

    @Override
    public void init() {
        ventaDAO = new VentaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idVenta = Long.parseLong(request.getParameter("idVenta"));
            int nuevoEstado = Integer.parseInt(request.getParameter("nuevoEstado")); // 1 o 2

            System.out.println(idVenta);
            System.out.println(nuevoEstado);

            boolean actualizado = ventaDAO.actualizarEstado(idVenta, nuevoEstado);

            if (actualizado) {
                response.sendRedirect(request.getContextPath() + "/ventas");
            } else {
                request.setAttribute("error", "No se pudo actualizar el estado de la venta.");
                request.getRequestDispatcher("/WEB-INF/views/ventas.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar el estado.");
            request.getRequestDispatcher("/WEB-INF/views/ventas.jsp").forward(request, response);
        }
    }
}
