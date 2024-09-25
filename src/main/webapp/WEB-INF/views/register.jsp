<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>register</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>
<header>
    <nav>
        <a href="index.jsp">Главная</a>
    </nav>
</header>

<main>
    <h2>Регистрация нового пользователя</h2>
    <form action="register" method="post">
        <div>
            <label for="name">Имя:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div>
            <label for="reg-email">Email:</label>
            <input type="email" id="reg-email" name="email" required>
        </div>
        <div>
            <label for="reg-password">Пароль:</label>
            <input type="password" id="reg-password" name="password" required>
        </div>
        <div>
            <label for="preferences">Предпочтения в кухне:</label>
            <select id="preferences" name="preferences">
                <option value="">Выберите предпочтение</option>
                <option value="Итальянская">Итальянская</option>
                <option value="Французская">Французская</option>
                <option value="Испанская">Испанская</option>
                <option value="Мексиканская">Мексиканская</option>
                <option value="Японская">Японская</option>
                <option value="Индийская">Индийская</option>
                <option value="Китайская">Китайская</option>
                <option value="Русская">Русская</option>
                <option value="Средиземноморская">Средиземноморская</option>
            </select>
        </div>
        <div>
            <button type="submit">Зарегистрироваться</button>
        </div>
    </form>

    <p>Уже есть аккаунт? <a href="/login">Войти</a></p>
</main>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
