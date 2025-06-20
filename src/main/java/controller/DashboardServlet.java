package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DashboardDAO;

public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final String URL = "jdbc:mysql://localhost:3306/pane";
    private final String USER = "root";
    private final String PASS = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            conn = DriverManager.getConnection(URL, USER, PASS);

            // Instanciar DAO con la conexión
            DashboardDAO dao = new DashboardDAO(conn);

            // Obtener métricas desde DAO
            int totalClientes = dao.obtenerTotalClientes();
            int totalVentas = dao.obtenerTotalVentas();
            double totalGanancias = dao.obtenerTotalGanancias();
            Map<String, Integer> ventasPorMes = dao.obtenerVentasPorMes();
            Map<String, Integer> inventarioPorCategoria = dao.obtenerInventarioPorCategoria();

            // Pasar los datos a la vista (JSP)
            request.setAttribute("totalClientes", totalClientes);
            request.setAttribute("totalVentas", totalVentas);
            request.setAttribute("totalGanancias", totalGanancias);
            request.setAttribute("ventasPorMes", ventasPorMes);
            request.setAttribute("inventarioPorCategoria", inventarioPorCategoria);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar los datos del dashboard.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }

        // Redirigir a dashboard.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
        rd.forward(request, response);
    }
}