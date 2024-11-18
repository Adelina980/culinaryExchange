<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Моя кулинарная книга</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/normalize.8.0.1.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="cookbook-container">
  <h1>Моя кулинарная книга</h1>

  <c:if test="${not empty user.createdRecipes}">
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
  <div class="recipes">
    <c:forEach var="preference" items="${preferences}">
      <h3 id="${preference}">${preference}</h3>
      <c:forEach var="recipe" items="${user.createdRecipes}" varStatus="status">
        <c:if test="${recipe.category == preference}">
          <div class="recipe-item">
<%--            <img src="${recipe.image}" alt="${recipe.title}" class="recipe-image">--%>
            <h4>
              <a href="${pageContext.request.contextPath}/recipe/${recipe.id}"> ${recipe.name}</a>
            </h4>
            <p>${recipe.description}</p>
            <p>Время приготовления: ${recipe.preparationTime} мин</p>
            <a href="${pageContext.request.contextPath}/recipe/edit/${recipe.id}" class="edit-btn">Редактировать</a>
            <form id="deleteForm-${recipe.id}" action="${pageContext.request.contextPath}/cookbook" method="post" style="display: none;">
                <input type="hidden" name="recipeId" value="${recipe.id}">
            </form>
            <a href="#" onclick="event.preventDefault(); document.getElementById('deleteForm-${recipe.id}').submit();" class="delete-btn">Удалить</a>

          </div>
        </c:if>
      </c:forEach>
    </c:forEach>
  </div>

  </c:if>

  <c:if test="${empty user.createdRecipes}">
    <p>Ваша кулинарная книга пуста. Сохраните рецепты, чтобы они отображались здесь.</p>
  </c:if>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

