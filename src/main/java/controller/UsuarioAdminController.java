package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.Usuario;
import model.UsuarioCliente;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import dao.ClienteDAO;
import dao.UsuarioAdminDAO;

@WebServlet("/usuarios")
public class UsuarioAdminController extends HttpServlet {

    private UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "listar" : action) {
            case "nuevo":
                request.getRequestDispatcher("/WEB-INF/views/nuevoUsuario.jsp").forward(request, response);
                break;
            case "editar":
                editarUsuario(request, response);
                break;
            case "eliminar":
                eliminarUsuario(request, response);
                break;
            default:
                listarUsuariosConClientes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "guardar" : action) {
            case "guardar":
                guardarUsuario(request, response);
                break;
            case "actualizar":
                actualizarUsuario(request, response);
                break;
            default:
                listarUsuariosConClientes(request, response);
                break;
        }
    }
    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            // Obtener el usuario y su cliente asociado desde la base de datos
            Usuario usuario = usuarioAdminDAO.obtenerUsuarioPorId(idUsuario);
            Cliente cliente = usuarioAdminDAO.obtenerClientePorIdUsuario(idUsuario);

            if (usuario == null || cliente == null) {
                request.setAttribute("error", "Usuario o cliente no encontrado.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            // Pasar el usuario y el cliente a la vista para mostrarlos en el formulario de edición
            request.setAttribute("usuario", usuario);
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("/WEB-INF/views/editarUsuario.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace(); // Log para depuración
            request.setAttribute("error", "Error al obtener el usuario o cliente desde la base de datos.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Log para depuración
            request.setAttribute("error", "ID de usuario no válido.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));  // Obtener el ID del usuario desde el formulario

            // Obtener los datos del formulario
            String correoUsuario = request.getParameter("correoUsuario");
            String contrasena = request.getParameter("contrasena");  // Contraseña (puede estar vacía)
            int idTipoUsuario = Integer.parseInt(request.getParameter("idTipoUsuario"));

            String nombreCliente = request.getParameter("nombre");
            String direccionCliente = request.getParameter("direccion");
            String telefonoCliente = request.getParameter("telefono");

            // Obtener el usuario existente para mantener la contraseña si no se proporciona una nueva
            Usuario usuarioExistente = usuarioAdminDAO.obtenerUsuarioPorId(idUsuario);

            // Si no se proporciona una nueva contraseña, mantenemos la actual
            if (contrasena == null || contrasena.isEmpty()) {
                contrasena = usuarioExistente.getContrasena();  // Mantener la contraseña actual
            } else {
                contrasena = encriptarContrasena(contrasena);  // Encriptar la nueva contraseña
            }

            // Crear las entidades Usuario y Cliente
            Usuario usuario = new Usuario(idUsuario, correoUsuario, contrasena, idTipoUsuario);
            Cliente cliente = new Cliente(0, nombreCliente, direccionCliente, telefonoCliente, idUsuario);  // El idCliente se maneja por la relación

            // Actualizar los datos en la base de datos
            if (usuarioAdminDAO.actualizarUsuarioYCliente(usuario, cliente)) {
                response.sendRedirect(request.getContextPath() + "/usuarios");
            } else {
                request.setAttribute("error", "No se pudo actualizar el usuario y cliente.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log para depuración
            request.setAttribute("error", "Error al actualizar el usuario o cliente en la base de datos.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Log para depuración
            request.setAttribute("error", "Formato de datos incorrecto.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }


    private void listarUsuariosConClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UsuarioCliente> usuariosClientes = usuarioAdminDAO.listarUsuariosConClientes();
            request.setAttribute("usuariosClientes", usuariosClientes);
            request.getRequestDispatcher("/WEB-INF/views/listaUsuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudieron listar los usuarios y clientes.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correoUsuario = request.getParameter("correoUsuario");
        String contrasena = request.getParameter("contrasena");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        int idTipoUsuario = Integer.parseInt(request.getParameter("idTipoUsuario"));

        if (correoUsuario == null || correoUsuario.isEmpty() || 
            contrasena == null || contrasena.isEmpty() || 
            nombre == null || nombre.isEmpty() || 
            direccion == null || direccion.isEmpty() || 
            telefono == null || telefono.isEmpty()) {
            
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/WEB-INF/views/nuevoUsuario.jsp").forward(request, response);
            return;
        }

        UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
        if (usuarioAdminDAO.existeUsuario(correoUsuario)) {
            request.setAttribute("error", "El correo ya está registrado. Intenta con otro.");
            request.getRequestDispatcher("/WEB-INF/views/nuevoUsuario.jsp").forward(request, response);
            return;
        }

        // Encriptar contraseña
        String contrasenaEncriptada = encriptarContrasena(contrasena);

        Usuario usuario = new Usuario(0, correoUsuario, contrasenaEncriptada, idTipoUsuario);

        try {
            int idUsuarioGenerado = usuarioAdminDAO.registrarUsuarioYRetornarId(usuario);

            if (idUsuarioGenerado > 0) {
                Cliente cliente = new Cliente(0, nombre, direccion, telefono, idUsuarioGenerado);
                ClienteDAO clienteDAO = new ClienteDAO();

                if (clienteDAO.registrarCliente(cliente)) {
                    request.getRequestDispatcher("/WEB-INF/views/registroExito.jsp").forward(request, response);
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


//metodo de controlador para eliminar usuario y cliente 
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        try {
            // Validar que el parámetro 'id' no sea nulo ni vacío
            if (idParam == null || idParam.isEmpty()) {
                throw new NumberFormatException("El ID del usuario no es válido.");
            }

            int idUsuario = Integer.parseInt(idParam); // Convertir el ID a entero

            // Llamar al método de DAO para eliminar el usuario
            boolean eliminado = usuarioAdminDAO.eliminarUsuario(idUsuario);

            if (eliminado) {
	                // Redirigir a la lista de usuarios si se elimina correctamente
                response.sendRedirect(request.getContextPath() + "/usuarios");
            } else { 
                // Mostrar mensaje de error si la eliminación falla
                request.setAttribute("error", "No se pudo eliminar el usuario.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "El ID del usuario no es válido.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Hubo un error al intentar eliminar el usuario.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }


    private String encriptarContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contrasena.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return contrasena;
    }
}
