<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Сброс пароля</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="reset-password-container">
    <h2>Сброс пароля</h2>
    <form action="send-reset-link" method="post">
        <div class="form-group">
            <label for="email">Введите ваш email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <button type="submit">Отправить ссылку для сброса пароля</button>
    </form>
</div>
</body>
</html>

