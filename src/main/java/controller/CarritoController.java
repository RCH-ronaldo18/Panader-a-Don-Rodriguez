package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carrito;

import java.io.IOException;

@WebServlet("/carrito")
public class CarritoController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mostrar el carrito
        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new Carrito(); // Inicializar carrito si es nulo
            session.setAttribute("carrito", carrito);
        }

        request.setAttribute("carrito", carrito);
        request.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "agregar":
                    agregarAlCarrito(request, response);
                    break;
                case "eliminar":
                    eliminarDelCarrito(request, response);
                    break;
                case "vaciar":
                    vaciarCarrito(request, response);
                    break;
                case "actualizar":
                    actualizarCarrito(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se recibió ninguna acción.");
        }
    }

    private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));

        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        carrito.agregarProducto(idProducto, cantidad, nombre, precio);
        response.sendRedirect("carrito");
    }

    private void eliminarDelCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null) {
            carrito.eliminarProducto(idProducto);
        }
        response.sendRedirect("carrito");
    }

    private void vaciarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null) {
            carrito.vaciarCarrito();
        }
        response.sendRedirect("carrito");
    }

    private void actualizarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null) {
            carrito.actualizarProducto(idProducto, cantidad);
        }
        response.sendRedirect("carrito");
    }
}

