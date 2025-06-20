<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.Map" %>
        <%@ page import="java.util.LinkedHashMap" %>
            <%@ page import="java.util.StringJoiner" %>
                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


                    <jsp:include page="includes/head.jsp" />

                    <body>
                        <jsp:include page="includes/header.jsp" />

                        <div class="container-fluid">
                            <div class="row">
                                <jsp:include page="includes/panelAdmin.jsp" />

                                <div class="col-md-9">
                                    <h2 class="text-center mb-4">Panel de Administración</h2>

                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="card text-white bg-success mb-3 shadow">
                                                <div class="card-body text-center">
                                                    <h5 class="card-title">Clientes</h5>
                                                    <p class="card-text fs-3">${totalClientes}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="card text-white bg-warning mb-3 shadow">
                                                <div class="card-body text-center">
                                                    <h5 class="card-title">Ventas</h5>
                                                    <p class="card-text fs-3">${totalVentas}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="card text-white bg-info mb-3 shadow">
                                                <div class="card-body text-center">
                                                    <h5 class="card-title">Ganancias</h5>
                                                    <p class="card-text fs-3">S/ ${totalGanancias}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Gráfico Ventas por Mes -->
                                    <div class="card shadow mb-4">
                                        <div class="card-body">
                                            <h5 class="card-title text-center">Ventas por Mes</h5>
                                            <canvas id="ventasChart" height="100"></canvas>
                                        </div>
                                    </div>

                                    <!-- Gráfico Inventario por Categoría -->
                                    <div class="card shadow mb-4">
                                        <div class="card-body">
                                            <h5 class="card-title text-center">Inventario por Categoría</h5>
                                            <canvas id="inventarioChart"
                                                style="max-width: 400px; max-height: 400px; margin: auto;"></canvas>

                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <jsp:include page="includes/footer.jsp" />

                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                        <script>
                            // Datos de ventas por mes
                            const ventasLabels = [
                                <c:forEach var="entry" items="${ventasPorMes}" varStatus="loop">
                                    "${entry.key}"<c:if test="${!loop.last}">,</c:if>
                                </c:forEach>
                            ];
                            const ventasData = [
                                <c:forEach var="entry" items="${ventasPorMes}" varStatus="loop">
                                    ${entry.value}<c:if test="${!loop.last}">,</c:if>
                                </c:forEach>
                            ];

                            const ventasChart = new Chart(document.getElementById('ventasChart'), {
                                type: 'bar',
                                data: {
                                    labels: ventasLabels,
                                    datasets: [{
                                        label: 'Ventas Mensuales',
                                        data: ventasData,
                                        backgroundColor: 'rgba(75, 192, 192, 0.6)'
                                    }]
                                }
                            });

                            // Datos de inventario por categoría
                            const inventarioLabels = [
                                <c:forEach var="entry" items="${inventarioPorCategoria}" varStatus="loop">
                                    "${entry.key}"<c:if test="${!loop.last}">,</c:if>
                                </c:forEach>
                            ];
                            const inventarioData = [
                                <c:forEach var="entry" items="${inventarioPorCategoria}" varStatus="loop">
                                    ${entry.value}<c:if test="${!loop.last}">,</c:if>
                                </c:forEach>
                            ];

                            const inventarioChart = new Chart(document.getElementById('inventarioChart'), {
                                type: 'pie',
                                data: {
                                    labels: inventarioLabels,
                                    datasets: [{
                                        label: 'Inventario por Categoría',
                                        data: inventarioData,
                                        backgroundColor: ['#007bff', '#ffc107', '#28a745', '#dc3545']
                                    }]
                                }
                            });
                        </script>

                    </body>