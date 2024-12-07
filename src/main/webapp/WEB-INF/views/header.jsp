<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<nav>
    <ul>
        <div class="logo">
            <li><img src="${pageContext.request.contextPath}/logo.png" alt="Иконка сайта" class="site-icon"></li>
            <li><h1 class="site-title">Culinary Exchange</h1></li>
        </div>
        <li><a href="${pageContext.request.contextPath}/">Главная</a></li>
        <li><a href="${pageContext.request.contextPath}/profile">Профиль</a></li>
        <li><a href="${pageContext.request.contextPath}/recipe/create">Создать рецепт</a></li>
        <li><a href="${pageContext.request.contextPath}/cookbook">Мои рецепты</a></li>
        <li><a href="${pageContext.request.contextPath}/favoriteRecipes">Любимые рецепты</a></li>
        <li><a href="${pageContext.request.contextPath}/search">Поиск рецептов</a></li>
        <li><a href="${pageContext.request.contextPath}/challenges">Челленджи</a></li>
        <li><a href="${pageContext.request.contextPath}/feedback">Обратная связь</a></li>
        <form action="${pageContext.request.contextPath}/search" method="get">
            <input type="text" name="query" placeholder="Поиск рецептов...">
            <button type="submit">Поиск</button>
        </form>
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty user}">
                    <p>Добро пожаловать, ${user.username}!</p>
                    <a href="/logout">Выйти</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Войти</a> | <a href="${pageContext.request.contextPath}/register">Зарегистрироваться</a>
                </c:otherwise>
            </c:choose>
        </div>
    </ul>

</nav>



<div class="messages">
    <c:if test="${not empty messages}">
        <c:forEach var="message" items="${messages}">
            <div class="message">${message}</div>
        </c:forEach>
    </c:if>
</div>


