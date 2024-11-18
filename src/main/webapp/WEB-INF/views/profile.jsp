<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="profile-container">
    <h1>Профиль пользователя</h1>

    <div class="user-info">
        <img src="${user.avatar}" alt="Аватар пользователя" class="avatar">
        <h2>${user.username}</h2>
        <p>Email: ${user.email}</p>
        <p>Любимые кухни:
            <c:forEach var="userPreferenceEl" items="${userPreference}">
        <ul>
            <li>
                    ${userPreferenceEl}
            </li>
        </ul>

        </c:forEach></p>
        <p>Рейтинг: ${userRating}</p>
        <p>Профиль был создан: ${createdAt}</p>
        <%--        <p>Достижения: ${user.achievements}</p>--%>
        <a href="${pageContext.request.contextPath}/profile/edit" class="btn">Редактировать профиль</a>
    </div>

    <div class="user-recipes">
        <h3>Мои рецепты</h3>
        <c:if test="${not empty createdRecipes}">
            <ul>
                <c:forEach var="recipe" items="${createdRecipes}">
                    <li>
                        <a href="/recipe/${recipe.id}">${recipe.name}</a>
                        <a href="${pageContext.request.contextPath}/recipe/edit/${recipe.id}" class="edit-btn">Редактировать</a>
                        <form id="deleteForm-${recipe.id}" action="${pageContext.request.contextPath}/cookbook"
                              method="post" style="display: none;">
                            <input type="hidden" name="recipeId" value="${recipe.id}">
                        </form>
                        <a href="#"
                           onclick="event.preventDefault(); document.getElementById('deleteForm-${recipe.id}').submit();"
                           class="delete-btn">Удалить</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty createdRecipes}">
            <p>У вас еще нет созданных рецептов.</p>
        </c:if>
    </div>

    <div class="saved-recipes">
        <h3>Избранные рецепты</h3>
        <c:if test="${not empty favoriteRecipes}">
            <ul>
                <c:forEach var="favoriteRecipe" items="${favoriteRecipes}">
                    <li>
                        <a href="/recipe/${favoriteRecipe.id}">${favoriteRecipe.name}</a>
                        <a href="#"
                           onclick="event.preventDefault(); document.getElementById('removeForm-${favoriteRecipe.id}').submit();"
                           class="remove-btn">Удалить из избранного</a>
                        <form id="removeForm-${favoriteRecipe.id}"
                              action="${pageContext.request.contextPath}/favoriteRecipes" method="post"
                              style="display: none;">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="recipeId" value="${favoriteRecipe.id}">
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty favoriteRecipes}">
            <p>У вас нет сохраненных рецептов.</p>
        </c:if>
    </div>

    <div class="interaction-history">
        <h3>История взаимодействий</h3>
        <c:if test="${not empty comments}">
            <ul>
                <c:forEach var="comment" items="${comments}">
                    <li>
                        <a href="${pageContext.request.contextPath}/recipe/${comment.recipe.id}#comment-${comment.id}">
                                ${comment.content}
                        </a> - ${comment.createdAt}
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty comments}">
            <p>У вас нет истории взаимодействий.</p>
        </c:if>
    </div>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

