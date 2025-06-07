package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.VentaDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carrito;
import model.DetalleVenta;
import model.Venta;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
@WebServlet("/registrarVenta")
public class RegistrarVentaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistrarVentaController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el carrito de la sesión
        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        
        if (carrito == null || carrito.getProductos().isEmpty()) {
            // Si el carrito está vacío, redirigir con un mensaje de error
            request.setAttribute("error", "El carrito está vacío. No se puede registrar la venta.");
            request.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(request, response);
            return;
        }
        
        try {
            // Obtener datos del cliente desde la solicitud o la sesión
            long idCliente = Long.parseLong(request.getParameter("idCliente"));
            double totalGeneral = Double.parseDouble(request.getParameter("total"));

            // Crear el objeto Venta
            Venta venta = new Venta();
            venta.setIdCliente(idCliente);
            venta.setFechaVenta(LocalDate.now()); // Fecha actual
            venta.setHoraVenta(LocalTime.now()); // Hora actual
            venta.setTotal(totalGeneral);

            // Crear los detalles de la venta
            List<DetalleVenta> detalles = new ArrayList<>();
            for (Integer idProducto : carrito.getProductos().keySet()) {
                int cantidad = carrito.getProductos().get(idProducto);
                double precioUnitario = carrito.getPrecioProducto(idProducto);
                double subtotal = precioUnitario * cantidad;
                double igv = subtotal * 0.18; // IGV 18%
                double total = subtotal + igv;

                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(precioUnitario);
                detalle.setSubtotal(subtotal);
                detalle.setIgv(igv);
                detalle.setTotal(total);
                venta.setIdEstado(1);
                detalles.add(detalle);
            }

            // Registrar la venta y sus detalles en la base de datos
            VentaDAO ventaDAO = new VentaDAO();
            boolean ventaExitosa = ventaDAO.registrarVentaConDetalles(venta, detalles);

            // Verificar si el inventario se actualizó correctamente
            for (DetalleVenta detalle : detalles) {
                boolean inventarioActualizado = ventaDAO.actualizarInventario(detalle.getIdProducto(), detalle.getCantidad());
                if (!inventarioActualizado) {
                    // Obtener cantidad disponible en el inventario
                    int cantidadDisponible = ventaDAO.obtenerCantidadDisponible(detalle.getIdProducto());
                    
                    // Crear un mensaje de error específico
                    String mensajeError = "No tenemos suficiente stock para este producto, solo tenemos " + cantidadDisponible + " unidades disponibles.";
                    
                    // Pasar el mensaje de error a la vista
                    request.setAttribute("error", mensajeError);
                    request.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(request, response);
                    return;
                }
            }

            // Limpiar el carrito después de registrar la venta
            carrito.vaciarCarrito();
            session.setAttribute("carrito", carrito);

            // Redirigir a una página de confirmación
            request.setAttribute("ventaExitosa", true);
            request.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al registrar la venta: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "El formato de los datos no es válido.");
            request.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(request, response);
        }
    }
}
