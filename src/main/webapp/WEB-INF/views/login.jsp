<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <link rel="stylesheet" href="styles.css">
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
        </div>
        <div>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <button type="submit">Войти</button>
        </div>
    </form>

<%--    <p><a href="forgot-password.jsp">Забыли пароль?</a></p>--%>

    <a class="nav-link text-center mt-3" href="/register">Регистрация</a>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
