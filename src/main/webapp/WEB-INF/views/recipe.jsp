<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${recipe.title}</title>
    <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="./styles/reset.css">
    <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>

<div class="recipe-container">
    <h1>${recipe.title}</h1>
    <img src="${recipe.imageUrl}" alt="${recipe.title}" class="recipe-image">

    <div class="recipe-details">
        <p><strong>Описание:</strong> ${recipe.description}</p>
        <p><strong>Время приготовления:</strong> ${recipe.cookingTime} минут</p>
        <p><strong>Порции:</strong> ${recipe.servings}</p>
        <p><strong>Сложность:</strong> ${recipe.difficulty}</p>

        <h2>Ингредиенты:</h2>
        <ul>
            <c:forEach var="ingredient" items="${recipe.ingredients}">
                <li>${ingredient}</li>
            </c:forEach>
        </ul>

        <h2>Шаги приготовления:</h2>
        <ol>
            <c:forEach var="step" items="${recipe.steps}">
                <li>${step}</li>
            </c:forEach>
        </ol>
    </div>

    <div class="actions">
        <form action="saveToFavorites.jsp" method="post">
            <input type="hidden" name="recipeId" value="${recipe.id}">
            <button type="submit">Сохранить рецепт</button>
        </form>

        <c:if test="${user.isAuthor}">
            <a href="editRecipe.jsp?id=${recipe.id}" class="edit-button">Редактировать</a>
        </c:if>
    </div>

    <div class="rating">
        <h2>Оцените рецепт:</h2>
        <form action="rateRecipe.jsp" method="post">
            <input type="hidden" name="recipeId" value="${recipe.id}">
            <select name="rating" required>
                <option value="">Выберите оценку</option>
                <c:forEach var="i" begin="1" end="5">
                    <option value="${i}">${i} звёзд</option>
                </c:forEach>
            </select>
            <button type="submit">Оценить</button>
        </form>
    </div>

    <div class="comments-section">
        <h2>Комментарии:</h2>
        <c:forEach var="comment" items="${recipe.comments}">
            <div class="comment">
                <p><strong>${comment.userName}:</strong> ${comment.text}</p>
                <p><em>${comment.date}</em></p>
            </div>
        </c:forEach>

        <h3>Добавить комментарий:</h3>
        <form action="addComment.jsp" method="post">
            <input type="hidden" name="recipeId" value="${recipe.id}">
            <textarea name="commentText" rows="4" required></textarea>
            <button type="submit">Отправить</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

