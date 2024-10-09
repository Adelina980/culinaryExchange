<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Создать рецепт</title>
  <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
  <link rel="stylesheet" href="./styles/reset.css">
  <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>

<div class="recipe-form-container">
  <h1>Создать новый рецепт</h1>

  <form action="saveRecipe.jsp" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="title">Название рецепта:</label>
      <input type="text" id="title" name="title" required>
    </div>

    <div class="form-group">
      <label for="description">Описание:</label>
      <textarea id="description" name="description" rows="4" required></textarea>
    </div>

    <div class="form-group">
      <label for="ingredients">Ингредиенты (через запятую):</label>
      <textarea id="ingredients" name="ingredients" rows="4" required></textarea>
    </div>

    <div class="form-group">
      <label for="steps">Шаги приготовления:</label>
      <textarea id="steps" name="steps" rows="6" required></textarea>
    </div>

    <div class="form-group">
      <label for="cookingTime">Время приготовления (в минутах):</label>
      <input type="number" id="cookingTime" name="cookingTime" required>
    </div>

    <div class="form-group">
      <label for="servings">Количество порций:</label>
      <input type="number" id="servings" name="servings" required>
    </div>

    <div class="form-group">
      <label for="image">Загрузить изображение:</label>
      <input type="file" id="image" name="image" accept="image/*" required>
    </div>

    <div class="form-group">
      <label for="category">Категория:</label>
      <select id="category" name="category" required>
        <option value="">Выберите категорию</option>
        <option value="Итальянская">Итальянская</option>
        <option value="Французская">Французская</option>
        <option value="Испанская">Испанская</option>
        <option value="Мексиканская">Мексиканская</option>
        <option value="Японская">Японская</option>
        <option value="Индийская">Индийская</option>
        <option value="Китайская">Китайская</option>
        <option value="Русская">Русская</option>
        <option value="Средиземноморская">Средиземноморская</option>
      </select>
    </div>


    <button type="submit">Опубликовать</button>
  </form>

  <c:if test="${not empty errorMessage}">
    <div class="error-message">${errorMessage}</div>
  </c:if>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

