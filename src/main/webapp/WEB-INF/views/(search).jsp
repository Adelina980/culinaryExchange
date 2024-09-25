<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Поиск рецептов</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<div class="search-container">
    <h1>Поиск рецептов</h1>
    <form action="searchResults.jsp" method="get">
        <input type="text" name="query" placeholder="Введите ключевые слова..." required>

        <label for="category">Категория:</label>
        <select name="category" id="category">
            <option value="">Все категории</option>
            <option value="breakfast">Завтраки</option>
            <option value="lunch">Обеды</option>
            <option value="dinner">Ужины</option>
            <option value="dessert">Десерты</option>
        </select>

        <label for="cookingTime">Время приготовления:</label>
        <select name="cookingTime" id="cookingTime">
            <option value="">Любое время</option>
            <option value="30">До 30 минут</option>
            <option value="60">До 60 минут</option>
            <option value="120">До 2 часов</option>
        </select>

        <button type="submit">Найти</button>
    </form>
</div>

<div class="results-container">
    <h2>Результаты поиска:</h2>
    <c:if test="${not empty recipes}">
        <ul class="recipe-list">
            <c:forEach var="recipe" items="${recipes}">
                <li class="recipe-item">
                    <a href="recipe.jsp?id=${recipe.id}">
                        <img src="${recipe.imageUrl}" alt="${recipe.title}" class="recipe-image">
                        <h3>${recipe.title}</h3>
                        <p>${recipe.shortDescription}</p>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty recipes}">
        <p>К сожалению, рецепты не найдены. Попробуйте изменить параметры поиска.</p>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

