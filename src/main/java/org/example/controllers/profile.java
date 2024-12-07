package org.example.controllers;

import org.example.dao.*;
import org.example.entity.*;
import org.example.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class profile extends HttpServlet {
    private UserDao userDao;
    private RecipeDao recipeDao;
    private UserPreferenceDao userPreferenceDao;
    private CommentDao commentDao;
    private RatingDao ratingDao;
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        recipeDao = (RecipeDao) getServletContext().getAttribute("recipeDao");
        userPreferenceDao = (UserPreferenceDao) getServletContext().getAttribute("userPreferenceDao");
        commentDao = (CommentDao) getServletContext().getAttribute("commentDao");
        ratingDao = (RatingDao) getServletContext().getAttribute("ratingDao");
        userService = (UserService) getServletContext().getAttribute("userService");


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = userService.getUser(request, response);

        if (currentUser == null) {
            // Если пользователь не авторизован, перенаправляем на страницу входа.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {


            User user = userDao.findById(currentUser.getId());
            List<Recipe> createdRecipes = recipeDao.findCreatedRecipesByUserId(user.getId());
            List<Recipe> favoriteRecipes = recipeDao.findFavoriteRecipesByUserId(user.getId());
            List<Comment> userComments = commentDao.findByUserId(user.getId());
            List<String> userPreference = userPreferenceDao.getPreferencesByUserId(user.getId());
            double userRating = ratingDao.calculateUserAverageRating(currentUser.getId());
            String createdAt = user.getCreatedAt();

            request.setAttribute("user", user);
            request.setAttribute("userPreference", userPreference);
            request.setAttribute("createdRecipes", createdRecipes);
            request.setAttribute("favoriteRecipes", favoriteRecipes);
            request.setAttribute("comments", userComments);
            request.setAttribute("userRating", userRating);
            request.setAttribute("createdAt", createdAt);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Не удалось загрузить профиль пользователя.");
//            getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        // Переход на страницу профиля
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}

