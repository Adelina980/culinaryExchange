<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Избранные рецепты</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="cookbook-container">
    <h1>Избранные рецепты</h1>

    <c:if test="${not empty user.favoriteRecipes}">
        <div class="categories">
            <h2>Категории</h2>
            <div class="categories">
                <ul>
                    <c:forEach var="preference" items="${preferences}">
                        <div>
                            <li><a href="#${preference}">${preference}</a></li>
                        </div>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="recipes">
            <c:forEach var="preference" items="${preferences}">
                <h3 id="${preference}">${preference}</h3>
                <c:forEach var="recipe" items="${user.favoriteRecipes}" varStatus="status">
                    <c:if test="${recipe.category == preference}">
                        <div class="recipe-item">
                                <%-- <img src="${recipe.image}" alt="${recipe.title}" class="recipe-image"> --%>
                            <h4>
                                <a href="${pageContext.request.contextPath}/recipe/${recipe.id}">${recipe.name}</a>
                            </h4>
                            <p>${recipe.description}</p>
                            <p>Время приготовления: ${recipe.preparationTime} мин</p>
                                    <a href="#" onclick="event.preventDefault(); document.getElementById('removeForm-${recipe.id}').submit();" class="remove-btn">Удалить из избранного</a>
                                    <form id="removeForm-${recipe.id}" action="${pageContext.request.contextPath}/favoriteRecipes" method="post" style="display: none;">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="recipeId" value="${recipe.id}">
                                    </form>

                        </div>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${empty user.favoriteRecipes}">
        <p>У вас пока нет избранных рецептов. Добавьте рецепты в избранное, чтобы они отображались здесь.</p>
    </c:if>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
