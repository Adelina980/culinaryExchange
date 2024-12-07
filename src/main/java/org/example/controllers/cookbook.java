package org.example.controllers;

import org.example.dao.*;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cookbook")
public class cookbook extends HttpServlet {
    private UserDao userDao;
    private RecipeDao recipeDao;
    private CommentDao commentDao;
    private RatingDao ratingDao;
    private PreferenceDao preferenceDao;
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        recipeDao = (RecipeDao) getServletContext().getAttribute("recipeDao");
        commentDao = (CommentDao) getServletContext().getAttribute("commentDao");
        ratingDao = (RatingDao) getServletContext().getAttribute("ratingDao");
        preferenceDao = (PreferenceDao) getServletContext().getAttribute("preferenceDao");
        userService = (UserService) getServletContext().getAttribute("userService");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);

        User user = userService.getUser(request, response); // Получение текущего пользователя

        if (user != null) {

            // Получение списка сохраненных рецептов пользователя
            List<Recipe> savedRecipes = recipeDao.findCreatedRecipesByUserId(user.getId());
            // Установка атрибута для передачи списка рецептов в JSP
            user.setCreatedRecipes(savedRecipes);
            request.setAttribute("user", user);
        } else {
            // Если пользователь не найден, перенаправить на страницу входа
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }

        // Переход на страницу cookbook.jsp
        request.getRequestDispatcher("/WEB-INF/views/cookbook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

