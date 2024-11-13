<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактировать рецепт</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/normalize.8.0.1.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="edit-recipe-container">
    <h1>Редактировать рецепт</h1>

    <form action="${pageContext.request.contextPath}/recipe/edit/${recipe.id}" method="post">
        <input type="hidden" name="recipeId" value="${recipe.id}"/>

        <div class="form-group">
            <label for="name">Название рецепта:</label>
            <input type="text" id="name" name="name" value="${recipe.name}" required/>
        </div>

        <div class="form-group">
            <label for="description">Описание:</label>
            <textarea id="description" name="description" required>${recipe.description}</textarea>
        </div>

        <div class="form-group">
            <label>Категория:</label>
            <div>
                <c:forEach var="preference" items="${preferences}">
                    <div>
                        <c:set var="isChecked" value="${fn:contains(recipe.category, preference)}" />
                        <input type="radio" id="preference-${preference}" name="preferences" value="${preference}"
                               <c:if test="${isChecked}">checked</c:if>/>
                        <label for="preference-${preference}">${preference}</label>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="form-group">
            <label for="preparationTime">Время приготовления (мин):</label>
            <input type="number" id="preparationTime" name="preparationTime" value="${recipe.preparationTime}" required/>
        </div>

        <div class="form-group">
            <label for="servings">Порции:</label>
            <input type="number" id="servings" name="servings" value="${recipe.servings}" required/>
        </div>

        <div class="form-group">
            <label for="ingridients">Ингредиенты:</label>
            <textarea id="ingridients" name="ingridients" required>${recipe.ingredients}</textarea>
        </div>

        <div class="form-group">
            <label for="steps">Шаги приготовления (через запятую):</label>
            <textarea id="steps" name="steps" required>${recipe.steps}</textarea>
        </div>


        <div class="form-group">
            <label>Дата создания:</label>
            <p>${createdAt}</p>
        </div>

        <button type="submit" class="btn btn-primary">Сохранить изменения</button>
        <a href="${previousPage != null ? previousPage : pageContext.request.contextPath + '/recipe/' + recipe.id}" class="btn btn-secondary">Отмена</a>

    </form>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

