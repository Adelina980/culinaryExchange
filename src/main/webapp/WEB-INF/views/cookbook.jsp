<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Моя кулинарная книга</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>

<div class="cookbook-container">
  <h1>Моя кулинарная книга</h1>

  <c:if test="${not empty user.savedRecipes}">
  <div class="categories">
    <h2>Категории</h2>
    <ul>
      <li><a href="#breakfasts">Завтраки</a></li>
      <li><a href="#lunches">Обеды</a></li>
      <li><a href="#dinners">Ужины</a></li>
      <li><a href="#desserts">Десерты</a></li>
    </ul>
  </div>

  <div class="recipes">

    <h3 id="breakfasts">Завтраки</h3>
    <c:forEach var="recipe" items="${user.savedRecipes}"
               varStatus="status">
      <c:if test="${recipe.category == 'Завтрак'}">
        <div class="recipe-item">
          <img src="${recipe.image}" alt="${recipe.title}" class="recipe-image">
          <h4>${recipe.title}</h4>
          <p>${recipe.description}</p>
          <p>Время приготовления: ${recipe.cookingTime} мин</p>
          <a href="removeFromCookbook.jsp?id=${recipe.id}" class="remove-btn">Удалить из кулинарной книги</a>
        </div>
      </c:if>
    </c:forEach>

    <h3 id="lunches">Обеды</h3>
    <c:forEach var="recipe" items="${user.savedRecipes}"
               varStatus="status">
      <c:if test="${recipe.category == 'Обед'}">
        <div class="recipe-item">
          <img src="${recipe.image}" alt="${recipe.title}" class="recipe-image">
          <h4>${recipe.title}</h4>
          <p>${recipe.description}</p>
          <p>Время приготовления: ${recipe.cookingTime} мин</p>
          <a href="removeFromCookbook.jsp?id=${recipe.id}" class="remove-btn">Удалить из кулинарной книги</a>
        </div>
      </c:if>
    </c:forEach>

    <h3 id="dinners">Ужины</h3>
    <c:forEach var="recipe" items="${user.savedRecipes}"
               varStatus="status">
      <c:if test="${recipe.category == 'Ужин'}">
        <div class="recipe-item">
          <img src="${recipe.image}" alt="${recipe.title}" class="recipe-image">
          <h4>${recipe.title}</h4>
          <p>${recipe.description}</p>
          <p>Время приготовления: ${recipe.cookingTime} мин</p>
          <a href="removeFromCookbook.jsp?id=${recipe.id}" class="remove-btn">Удалить из кулинарной книги</a>
        </div>
      </c:if>
    </c:forEach>

    <h3 id="desserts">Десерты</h3>
    <c:forEach var="recipe" items="${user.savedRecipes}"
               varStatus="status">
      <c:if test="${recipe.category == 'Десерт'}">
        <div class="recipe-item">
          <img src="${recipe.image}" alt="${recipe.title}" class="recipe-image">
          <h4>${recipe.title}</h4>
          <p>${recipe.description}</p>
          <p>Время приготовления: ${recipe.cookingTime} мин</p>
          <a href="removeFromCookbook.jsp?id=${recipe.id}" class="remove-btn">Удалить из кулинарной книги</a>
        </div>
      </c:if>
    </c:forEach>

  </div>

  </c:if>

  <c:if test="${empty user.savedRecipes}">
    <p>Ваша кулинарная книга пуста. Сохраните рецепты, чтобы они отображались здесь.</p>
  </c:if>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

