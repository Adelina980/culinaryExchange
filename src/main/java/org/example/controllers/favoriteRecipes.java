package org.example.controllers;

import org.example.dao.PreferenceDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
import org.example.dao.UserFavoriteRecipesDao;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/favoriteRecipes")
public class favoriteRecipes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreferenceDao preferenceDao = new PreferenceDao();
        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);

        UserService userService = new UserService();
        User user = userService.getUser(request, response); // Получение текущего пользователя

        if (user != null) {
            RecipeDao recipeDao = new RecipeDao();

            List<Recipe> favoriteRecipes = recipeDao.findFavoriteRecipesByUserId(user.getId());
            // Установка атрибута для передачи списка рецептов в JSP
            user.setFavoriteRecipes(favoriteRecipes);
            request.setAttribute("user", user);
        } else {
            // Если пользователь не найден, перенаправить на страницу входа
            response.sendRedirect(getServletContext().getContextPath()+"/login");
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/favoriteRecipes.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserService userService = new UserService();
        User user = userService.getUser(request, response);

        if ("remove".equals(action)) {
            try {
                Long recipeId = Long.parseLong(request.getParameter("recipeId"));
                UserFavoriteRecipesDao userFavoriteRecipesDao = new UserFavoriteRecipesDao();
                userFavoriteRecipesDao.removeRecipeFromFavorites(user, recipeId);
                response.sendRedirect(request.getContextPath() + "/favoriteRecipes");
            } catch (DbException | NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Ошибка при удалении рецепта из избранного.");
                request.getRequestDispatcher("/favoriteRecipes.jsp").forward(request, response);
            }
        }
    }

}
