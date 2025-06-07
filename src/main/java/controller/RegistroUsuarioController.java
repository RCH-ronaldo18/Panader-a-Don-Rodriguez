package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.Usuario;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dao.ClienteDAO;
import dao.UsuarioDAO;

@WebServlet("/registro")
// registro por parte del cliente 
public class RegistroUsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Solo mostramos modal si exito=true en la URL
    //   String exito = request.getParameter("exito");
      //  if ("true".equals(exito)) {
        //    request.setAttribute("registroExitoso", true);
        //}
        request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
    } 
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener datos del formulario de usuario
        String correoUsuario = request.getParameter("correoUsuario");
        String contrasena = request.getParameter("contrasena");

        // Obtener datos del formulario de cliente
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");

        // Validar campos vacíos
        if (correoUsuario == null || correoUsuario.isEmpty() || 
            contrasena == null || contrasena.isEmpty() || 
            nombre == null || nombre.isEmpty() || 
            direccion == null || direccion.isEmpty() || 
            telefono == null || telefono.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
            return;
        }

        // Verificar si el usuario ya está registrado
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.existeUsuario(correoUsuario)) {
            request.setAttribute("error", "El correo ya está registrado. Intenta con otro.");
            request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
            return;
        }

        // Encriptar contraseña
        String contrasenaEncriptada = encriptarContrasena(contrasena);

        // Crear objeto Usuario
        Usuario usuario = new Usuario(0, correoUsuario, contrasenaEncriptada, 3); // rol '3' para 'usuario' por defecto

        try { 
            // Registrar usuario
            int idUsuarioGenerado = usuarioDAO.registrarUsuarioYRetornarId(usuario);

            if (idUsuarioGenerado > 0) {
                // Crear objeto Cliente con el id_usuario generado
                Cliente cliente = new Cliente(0, nombre, direccion, telefono, idUsuarioGenerado);
                ClienteDAO clienteDAO = new ClienteDAO();
                if (clienteDAO.registrarCliente(cliente)) {
                    // Redirigir con parámetro de éxito
                    request.getSession().setAttribute("registroExitoso", true);
                    response.sendRedirect("registro");
                } else {
                    request.setAttribute("error", "Error al registrar los datos del cliente.");
                    request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Error al registrar el usuario.");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
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
}
