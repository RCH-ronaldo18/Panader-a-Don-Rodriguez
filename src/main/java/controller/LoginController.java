package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dao.UsuarioDAO;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cargar la vista de inicio de sesión
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correoUsuario = request.getParameter("correoUsuario"); // Updated parameter name
        String contrasena = request.getParameter("contrasena");

        if (correoUsuario == null || correoUsuario.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // Encriptar la contraseña ingresada por el usuario con SHA-256
        String contrasenaEncriptada = encriptarContrasena(contrasena);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuario = usuarioDAO.validarUsuario(correoUsuario, contrasenaEncriptada);

            if (usuario != null) {
                // Guardar el correo electrónico y el tipo de usuario en la sesión
                request.getSession().setAttribute("correoUsuario", usuario.getCorreo_usuario());
                request.getSession().setAttribute("idTipoUsuario", usuario.getId_tipo_usuario());
                request.getSession().setAttribute("idUsuario", usuario.getId_usuario()); // Suponiendo que tienes un método getId()

                // Redirigir según el tipo de usuario
                switch (usuario.getId_tipo_usuario()) {
                    case 1: // Administrador
                    	 request.getRequestDispatcher("/WEB-INF/views/administrador.jsp").forward(request, response);
                        break;
                    case 2: // Vendedor
                        request.getRequestDispatcher("/WEB-INF/views/vendedor.jsp").forward(request, response);
                        break;
                    case 3: // Usuario
                        request.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(request, response);
                        break;
                    default:
                        request.setAttribute("error", "Tipo de usuario desconocido.");
                        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("error", "Correo electrónico o contraseña incorrectos."); // Updated error message
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // Método para encriptar la contraseña con SHA-256
    private String encriptarContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contrasena.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Método para cerrar sesión
    @WebServlet("/cerrarSesion")
    public static class LogoutServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false); // Obtener la sesión actual
            if (session != null) {
                session.invalidate(); // Invalidar la sesión
            }
            request.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(request, response); // Redirigir a la página de inicio de sesión
        }
    }
}
