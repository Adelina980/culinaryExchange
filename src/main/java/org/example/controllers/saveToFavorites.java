package org.example.controllers;

import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
import org.example.dao.UserFavoriteRecipesDao;
import org.example.service.UserService;
import org.example.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/saveToFavorites")
public class saveToFavorites extends HttpServlet {
    private UserFavoriteRecipesDao userFavoriteRecipesDao;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        // Инициализация сервисов
        userFavoriteRecipesDao = new UserFavoriteRecipesDao();
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем ID рецепта из запроса
        String recipeIdParam = request.getParameter("recipeId");
        if (recipeIdParam == null || recipeIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Идентификатор рецепта не указан");
            return;
        }

        Long recipeId;
        try {
            recipeId = Long.parseLong(recipeIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат идентификатора рецепта");
            return;
        }


//        User currentUser = (User) request.getSession().getAttribute("currentUser");
        User currentUser = userService.getUser(request, response);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Добавляем рецепт в избранное
        try {
            userFavoriteRecipesDao.addRecipeToFavorites(currentUser, recipeId);
            response.sendRedirect(request.getContextPath() + "/favoriteRecipes");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при обработке запроса");
        }
    }

}
