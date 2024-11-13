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

@WebServlet("/recipe/edit/*")
public class editRecipe extends HttpServlet {

    private final RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreferenceDao preferenceDao = new PreferenceDao();
        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);

        UserService userService = new UserService();
        User user = userService.getUser(request, response);
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Параметр id отсутствует");
            return;
        }

        Long recipeId;
        try {
            recipeId = Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат id");
            return;
        }

        Recipe recipe = recipeDao.findById(recipeId);
        String createdAt = recipe.getCreatedAt();

        if (recipe != null && user.getId().equals(recipe.getUser().getId())) {
            String previousPage = request.getHeader("Referer");
            request.setAttribute("previousPage", previousPage);

            request.setAttribute("recipe", recipe);
            request.setAttribute("createdAt", createdAt);
            request.getRequestDispatcher("/WEB-INF/views/editRecipe.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Вы не можете редактировать этот рецепт.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        UserService userService = new UserService();
        User user = userService.getUser(request, response);
        Long recipeId = Long.parseLong(request.getParameter("recipeId"));

        try {
            Recipe recipe = recipeDao.findById(recipeId);

            if (recipe != null && user.getId().equals(recipe.getUser().getId())) {

                String newName = request.getParameter("name");
                String newDescription = request.getParameter("description");
                String newCategory = request.getParameter("preferences");
                int newPreparationTime = Integer.parseInt(request.getParameter("preparationTime"));
                int newServings = Integer.parseInt(request.getParameter("servings"));
                String newIngridients = request.getParameter("ingridients");
                String newSteps = request.getParameter("steps");


                recipe.setName(newName);
                recipe.setDescription(newDescription);
                recipe.setCategory(newCategory);
                recipe.setPreparationTime(newPreparationTime);
                recipe.setServings(newServings);
                recipe.setIngredients(newIngridients);
                recipe.setSteps(newSteps);

                recipeDao.updateRecipe(recipe);
                response.sendRedirect(request.getContextPath() + "/recipe/" + recipeId);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Вы не можете редактировать этот рецепт.");
            }
        } catch (DbException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при обновлении рецепта.");
        }
    }
}

