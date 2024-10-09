<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Администрирование - Кулинарный обмен</title>
  <link rel="stylesheet" href="./styles/normalize.8.0.1.css">
  <link rel="stylesheet" href="./styles/reset.css">
  <link rel="stylesheet" href="./styles/styles.css">
</head>
<body>

<div class="admin-container">
  <h1>Страница администрирования</h1>

  <div class="statistics">
    <h2>Статистика приложения</h2>
    <p>Количество зарегистрированных пользователей: ${userCount}</p>
    <p>Количество добавленных рецептов: ${recipeCount}</p>
    <p>Количество активных пользователей: ${activeUserCount}</p>
  </div>

  <div class="user-management">
    <h2>Управление пользователями</h2>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Email</th>
        <th>Статус</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="user" items="${userList}">
        <tr>
          <td>${user.id}</td>
          <td>${user.name}</td>
          <td>${user.email}</td>
          <td>${user.status}</td>
          <td>
            <form action="blockUser.jsp" method="post">
              <input type="hidden" name="userId" value="${user.id}">
              <button type="submit">Заблокировать</button>
            </form>
            <form action="deleteUser.jsp" method="post">
              <input type="hidden" name="userId" value="${user.id}">
              <button type="submit">Удалить</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>

  <div class="recipe-management">
    <h2>Управление рецептами</h2>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Название рецепта</th>
        <th>Автор</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="recipe" items="${recipeList}">
        <tr>
          <td>${recipe.id}</td>
          <td>${recipe.title}</td>
          <td>${recipe.authorName}</td>
          <td>
            <form action="deleteRecipe.jsp" method="post">
              <input type="hidden" name="recipeId" value="${recipe.id}">
              <button type="submit">Удалить</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>

</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
