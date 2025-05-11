<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.Usuario"%>
<%@ page import="model.Cliente"%>
<%@ page import="java.util.List"%>
<%@ page import="model.UsuarioCliente"%>
<%
Integer userType = (Integer) session.getAttribute("idTipoUsuario");
if (userType == null || userType != 1) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<html>
<head>
    <jsp:include page="includes/head.jsp" />
</head>
<body>
    <jsp:include page="includes/header.jsp" />
    <h2 class="text-center">Lista de Usuarios</h2>
    <div class="container-fluid">
        <div class="row">
            <!-- Panel de administración -->
            <jsp:include page="includes/panelAdmin.jsp"></jsp:include>
            
            <div class="col-md-9">
                <div class="row">
                    <div class="col-md-12">
                        <!-- Formulario para agregar nuevo usuario -->
                        <div class="d-flex justify-content-md-end">
                            <a href="<%=request.getContextPath()%>/usuarios?action=nuevo" class="btn btn-info">Agregar Usuario</a>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID Usuario</th>
                                <th>Correo</th>
                                <th>Tipo de Usuario</th>
                                <th>Nombre Cliente</th>
                                <th>Dirección</th>
                                <th>Teléfono</th>
                                <th colspan="2">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            List<UsuarioCliente> usuariosClientes = (List<UsuarioCliente>) request.getAttribute("usuariosClientes");
                            if (usuariosClientes != null) {
                                for (UsuarioCliente usuarioCliente : usuariosClientes) {
                                    Usuario usuario = usuarioCliente.getUsuario();
                                    Cliente cliente = usuarioCliente.getCliente();
                            %>
                            <tr>
                                <td><%=usuario.getId_usuario()%></td>
                                <td><%=usuario.getCorreo_usuario()%></td>
                                <td>
                                    <%
                                    switch (usuario.getId_tipo_usuario()) {
                                        case 1:
                                            out.print("Administrador");
                                            break;
                                        case 2:
                                            out.print("Vendedor");
                                            break;
                                        default:
                                            out.print("Usuario");
                                            break;
                                    }
                                    %>
                                </td>
                                <td><%=cliente != null ? cliente.getNombre() : "N/A"%></td>
                                <td><%=cliente != null ? cliente.getDireccion() : "N/A"%></td>
                                <td><%=cliente != null ? cliente.getTelefono() : "N/A"%></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/usuarios?action=editar&idUsuario=<%=usuario.getId_usuario()%>&tipoUsuario=<%=usuario.getId_tipo_usuario()%>&idCliente=<%=cliente != null ? cliente.getIdCliente() : 0%>" class="btn btn-warning">Editar</a>
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/usuarios?action=eliminar&id=<%=usuario.getId_usuario()%>" onclick="return confirm('¿Estás seguro de que quieres eliminar este usuario?');" class="btn btn-danger">Eliminar</a>
                                </td>    
                            </tr>
                            <%
                            }
                            } else {
                            %>
                            <tr>
                                <td colspan="7" class="text-center">No hay usuarios registrados.</td>
                            </tr>
                            <%
                            }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="includes/footer.jsp" />
</body>
</html>


