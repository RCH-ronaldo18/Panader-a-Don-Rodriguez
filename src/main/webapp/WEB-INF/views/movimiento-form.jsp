<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="model.Producto" %>
        <%@ page import="java.util.List" %>

            <% String action=request.getParameter("action") !=null && request.getParameter("action").equals("update")
                ? "Actualizar" : "Registrar" ; List<Producto> productos = (List<Producto>)
                    request.getAttribute("productos");
                    %>

                    <html lang="es">

                    <head>
                        <jsp:include page="includes/head.jsp" />
                    </head>

                    <body>
                        <jsp:include page="includes/header.jsp" />
                        <h1 class="text-center">
                            <%= action %> Movimiento
                        </h1>

                        <div class="container">
                            <div class="row">
                                <form action="movimientos" method="post">
                                    <input type="hidden" name="action" value="guardar">

                                    <!-- Producto -->
                                    <div class="input-group flex-nowrap mb-2">
                                        <span class="input-group-text" id="addon-wrapping">Producto</span>
                                        <select class="form-control" name="id_producto" required>
                                            <option value="" disabled selected>Seleccione un producto</option>
                                            <% if (productos !=null) { for (Producto p : productos) { %>
                                                <option value="<%= p.getId_producto() %>">
                                                    <%= p.getNombre() %>
                                                </option>
                                                <% } } %>
                                        </select>
                                    </div>

                                    <!-- Cantidad -->
                                    <div class="input-group flex-nowrap mb-2">
                                        <span class="input-group-text" id="addon-wrapping">Cantidad</span>
                                        <input type="number" name="cantidad" class="form-control" required min="1">
                                    </div>

                                    <!-- Tipo de Pago -->
                                    <div class="input-group flex-nowrap mb-2">
                                        <span class="input-group-text" id="addon-wrapping">Tipo de Pago</span>
                                        <select class="form-control" name="id_tipo_pago" required>
                                            <option value="" disabled selected>Seleccione un tipo</option>
                                            <option value="1">Efectivo</option>
                                            <option value="2">Tarjeta</option>
                                            <option value="3">Otro</option>
                                        </select>
                                    </div>

                                    <!-- Descripción -->
                                    <div class="input-group flex-nowrap mb-2">
                                        <span class="input-group-text" id="addon-wrapping">Descripción</span>
                                        <input type="text" name="descripcion" class="form-control" maxlength="100"
                                            required>
                                    </div>

                                    <!-- Botón -->
                                    <div class="my-3">
                                        <button class="btn btn-success" type="submit">
                                            <%= action %>
                                        </button>
                                    </div>
                                </form>

                                <a href="movimientos?action=listar" class="btn btn-secondary btn-sm">Volver al
                                    historial</a>
                            </div>
                        </div>

                        <jsp:include page="includes/footer.jsp" />
                    </body>

                    </html>