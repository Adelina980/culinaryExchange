<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.controllers.User" %>

<nav>
    <ul>
        <li><img src="logo.png" alt="Иконка сайта" class="site-icon"></li>
        <li><h1 class="site-title">Culinary Exchange</h1></li>
        <li><a href="/">Главная</a></li>
        <li><a href="/profile">Профиль</a></li>
        <li><a href="/create-recipe">Создать рецепт</a></li>
        <li><a href="/cookbook">Кулинарная книга</a></li>
        <li><a href="/search">Поиск рецептов</a></li>
<%--        <li><a href="/challenges">Челленджи</a></li>--%>
        <li><a href="/feedback">Обратная связь</a></li>
    </ul>
</nav>

<div class="user-info">
    <c:choose>
        <c:when test="${not empty user}">
            <p>Добро пожаловать, ${user.name}!</p>
            <a href="/logout">Выйти</a>
        </c:when>
        <c:otherwise>
            <a href="/login">Войти</a> | <a href="/register">Зарегистрироваться</a>
        </c:otherwise>
    </c:choose>
</div>

<form action="/search" method="get">
    <input type="text" name="query" placeholder="Поиск рецептов...">
    <button type="submit">Поиск</button>
</form>

<div class="messages">
    <c:if test="${not empty messages}">
        <c:forEach var="message" items="${messages}">
            <div class="message">${message}</div>
        </c:forEach>
    </c:if>
</div>


