<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="./styles/reset.css">
    <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>

<div class="profile-container">
    <h1>Профиль пользователя</h1>

    <div class="user-info">
        <img src="${user.avatar}" alt="Аватар пользователя" class="avatar">
        <h2>${user.name}</h2>
        <p>Email: ${user.email}</p>
        <p>Любимые кухни: ${userPreference.getPreference().getPreferenceName()}</p>
<%--        <p>Уровень кулинарного мастерства: ${user.skillLevel}</p>--%>
<%--        <p>Достижения: ${user.achievements}</p>--%>
        <a href="editProfile.jsp" class="btn">Редактировать профиль</a>
    </div>

    <div class="user-recipes">
        <h3>Мои рецепты</h3>
        <c:if test="${not empty user.recipes}">
            <ul>
                <c:forEach var="recipe" items="${user.recipes}">
                    <li>
                        <a href="recipeDetail.jsp?id=${recipe.id}">${recipe.title}</a>
                        <a href="editRecipe.jsp?id=${recipe.id}" class="edit-btn">Редактировать</a>
                        <a href="deleteRecipe.jsp?id=${recipe.id}" class="delete-btn">Удалить</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty user.recipes}">
            <p>У вас еще нет созданных рецептов.</p>
        </c:if>
    </div>

    <div class="saved-recipes">
        <h3>Сохраненные рецепты</h3>
        <c:if test="${not empty user.savedRecipes}">
            <ul>
                <c:forEach var="savedRecipe" items="${user.savedRecipes}">
                    <li>
                        <a href="recipeDetail.jsp?id=${savedRecipe.id}">${savedRecipe.title}</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty user.savedRecipes}">
            <p>У вас нет сохраненных рецептов.</p>
        </c:if>
    </div>

    <div class="interaction-history">
        <h3>История взаимодействий</h3>
        <c:if test="${not empty user.interactions}">
            <ul>
                <c:forEach var="interaction" items="${user.interactions}">
                    <li>${interaction.comment} - ${interaction.date}</li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty user.interactions}">
            <p>У вас нет истории взаимодействий.</p>
        </c:if>
    </div>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

