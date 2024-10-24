<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="./styles/reset.css">
    <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>
<header>
    <nav>
        <a href="/main">Главная</a>
    </nav>
</header>

<main>
    <h2>Вход в систему</h2>
    <form action="login" method="post">
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            <c:if test="${not empty errors['email']}">
                <span class="error">${errors['email']}</span>
            </c:if>
        </div>
        <div>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
            <c:if test="${not empty errors['password']}">
                <span class="error">${errors['password']}</span>
            </c:if>
        </div>
        <div>
            <button type="submit">Войти</button>
        </div>
    </form>


    <a class="nav-link text-center mt-3" href="/register">Регистрация</a>
<%--    <a href="/forgotPassword">Забыли пароль?</a>--%>


<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
