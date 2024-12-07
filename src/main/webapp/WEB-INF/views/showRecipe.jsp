<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${recipe.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>
<div class="recipe-container">
    <h1>${recipe.name}</h1>
<%--    <img src="${recipe.imageUrl}" alt="${recipe.title}" class="recipe-image">--%>

    <div class="recipe-details">
        <p><strong>Описание:</strong> ${recipe.description}</p>
        <p><strong>Время приготовления:</strong> ${recipe.preparationTime} минут</p>
        <p><strong>Порции:</strong> ${recipe.servings}</p>


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
        <p><strong>Дата создания:</strong> ${recipe.createdAt}</p>
    </div>

    <div class="actions">
        <form action="${pageContext.request.contextPath}/saveToFavorites" method="post">
            <input type="hidden" name="recipeId" value="${recipe.id}">
            <button type="submit">Сохранить рецепт в любимые</button>
        </form>

        <c:if test="${recipe.user.id == user.id}">
            <a href="${pageContext.request.contextPath}/recipe/edit/${recipe.id}" class="edit-btn">Редактировать</a>
            <form id="deleteForm-${recipe.id}" action="${pageContext.request.contextPath}/recipe/${recipe.id}" method="post" style="display: none;">
                <input type="hidden" name="recipeId" value="${recipe.id}">
            </form>
            <a href="#" onclick="event.preventDefault(); document.getElementById('deleteForm-${recipe.id}').submit();" class="delete-btn">Удалить</a>

        </c:if>
    </div>

    <div class="rating">
        <h2>Средняя оценка: ${averageRating}</h2>
        <form action="${pageContext.request.contextPath}/recipe/${recipe.id}" method="post">
            <input type="hidden" name="action" value="rate">
            <input type="hidden" name="recipeId" value="${recipe.id}">
            <select name="rating" required>
                <option value="">Выберите оценку</option>
                <c:forEach var="i" begin="1" end="5">
                    <option value="${i}">
                        <c:choose>
                            <c:when test="${i == 1}">
                                ${i} звезда
                            </c:when>
                            <c:when test="${i >= 2 && i <= 4}">
                                ${i} звезды
                            </c:when>
                            <c:otherwise>
                                ${i} звёзд
                            </c:otherwise>
                        </c:choose>
                    </option>
                </c:forEach>
            </select>
            <button type="submit">Оценить</button>
        </form>
    </div>

    <div class="comments-section" id="comment-${comment.id}">

        <h3>Добавить комментарий:</h3>
        <form action="${pageContext.request.contextPath}/recipe/${recipe.id}" method="post">
            <input type="hidden" name="action" value="addComment">
            <textarea name="commentText" rows="4" required></textarea>
            <button type="submit">Отправить</button>
        </form>
        <h2>Комментарии:</h2>
        <c:choose>
            <c:when test="${not empty comments}">
                <c:forEach var="comment" items="${comments}">
                    <div class="comment" id="comment-${comment.id}">
                        <p><strong>${comment.user.username}:</strong> ${comment.content}</p>
                        <p><em>${comment.createdAt}</em></p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Комментариев пока нет</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

