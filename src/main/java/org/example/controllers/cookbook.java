package org.example.controllers;

import org.example.dao.PreferenceDao;
import org.example.dao.RecipeDao;
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

@WebServlet("/cookbook")
public class cookbook extends HttpServlet {
    private RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreferenceDao preferenceDao = new PreferenceDao();
        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);

        UserService userService = new UserService();
        User user = userService.getUser(request, response); // Получение текущего пользователя

        if (user != null) {
            RecipeDao recipeDao;
            recipeDao = new RecipeDao();
            // Получение списка сохраненных рецептов пользователя
            List<Recipe> savedRecipes = recipeDao.findCreatedRecipesByUserId(user.getId());
            // Установка атрибута для передачи списка рецептов в JSP
            user.setCreatedRecipes(savedRecipes);
            request.setAttribute("user", user);
        } else {
            // Если пользователь не найден, перенаправить на страницу входа
            response.sendRedirect(getServletContext().getContextPath()+"/login");
            return;
        }

        // Переход на страницу cookbook.jsp
        getServletContext().getRequestDispatcher("/WEB-INF/views/cookbook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        User user = userService.getUser(request, response);
        Long recipeId = Long.parseLong(request.getParameter("recipeId"));

        Recipe recipe = recipeDao.findById(recipeId);


        try {
            if (user.getId().equals(recipe.getUser().getId())) {
                recipeDao.deleteRecipe(recipeId);
                response.sendRedirect(request.getContextPath() + "/cookbook");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Вы не можете удалить этот рецепт.");
            }
        } catch (DbException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при удалении рецепта.");
        }
    }

}

