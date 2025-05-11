<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    Integer userType = (Integer) session.getAttribute("idTipoUsuario");
    if (userType == null || userType != 1) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<jsp:include page="includes/head.jsp" />
<body>
<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/panelAdmin.jsp" />
<jsp:include page="includes/footer.jsp" />
</body>
</html>