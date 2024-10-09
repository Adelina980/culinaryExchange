<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Кулинарные челленджи</title>
    <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="./styles/reset.css">
    <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>
<header>
    <h1>Кулинарные челленджи</h1>
    <nav>
        <ul>
            <li><a href="index.jsp">Главная</a></li>
            <li><a href="profile.jsp">Профиль</a></li>
            <li><a href="logout.jsp">Выйти</a></li>
        </ul>
    </nav>
</header>

<main>
    <section id="challenges">
        <h2>Текущие челленджи</h2>
        <c:if test="${not empty challenges}">
            <ul>
                <c:forEach var="challenge" items="${challenges}">
                    <li>
                        <h3>${challenge.title}</h3>
                        <p>${challenge.description}</p>
                        <p>Срок окончания: ${challenge.endDate}</p>
                        <form action="submitChallengeEntry.jsp" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="challengeId" value="${challenge.id}">
                            <label for="photo">Загрузите фото вашего блюда:</label>
                            <input type="file" name="photo" accept="image/*" required>
                            <button type="submit">Участвовать!</button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${empty challenges}">
            <p>В данный момент нет активных челленджей.</p>
        </c:if>
    </section>

    <section id="results">
        <h2>Результаты прошлых челленджей</h2>
        <!-- Здесь можно добавить логику для отображения результатов прошлых челленджей -->
    </section>
</main>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

