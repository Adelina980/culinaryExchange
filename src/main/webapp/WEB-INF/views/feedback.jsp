<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Обратная связь</title>
    <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="./styles/reset.css">
    <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>

<div class="feedback-container">
    <h1>Обратная связь</h1>
    <p>Мы рады вашим отзывам и предложениям! Пожалуйста, заполните форму ниже.</p>

    <form action="submitFeedback.jsp" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">Ваше имя:</label>
            <input type="text" id="name" name="name" required>
        </div>

        <div class="form-group">
            <label for="email">Ваш email:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="message">Ваш отзыв или предложение:</label>
            <textarea id="message" name="message" rows="5" required></textarea>
        </div>

        <div class="form-group">
            <label for="image">Прикрепить изображение (опционально):</label>
            <input type="file" id="image" name="image" accept="image/*">
        </div>

        <button type="submit">Отправить</button>
    </form>

    <c:if test="${not empty feedbackMessage}">
        <div class="feedback-message">
            <p>${feedbackMessage}</p>
        </div>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
