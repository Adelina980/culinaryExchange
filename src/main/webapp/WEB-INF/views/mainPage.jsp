<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>CulinaryExchange</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<c:set var="contentPage" value="contentBody.jsp" />

<jsp:include page="${contentPage}" />

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>




