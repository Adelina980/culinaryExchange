package org.example.controllers;

import org.example.dao.CommentDao;
import org.example.dao.RatingDao;
import org.example.dao.RecipeDao;
import org.example.entity.Comment;
import org.example.entity.Rating;
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

@WebServlet("/recipe/*")
public class showRecipe extends HttpServlet {
    private RecipeDao recipeDao = new RecipeDao();
    private RatingDao ratingDao = new RatingDao();
    private CommentDao commentDao = new CommentDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo(); // Получаем часть URL после /recipe

        if (pathInfo != null && pathInfo.matches("^/\\d+$")) {
            String recipeIdStr = pathInfo.substring(1); // Удаляем начальный слэш
            try {
                Long recipeId = Long.parseLong(recipeIdStr);
                // Логика для загрузки рецепта по ID
                Recipe recipe = recipeDao.findById(recipeId);

                if (recipe != null) {
                    // Передача объекта рецепта в JSP
                    request.setAttribute("recipe", recipe);

                    List<Comment> comments = commentDao.findByRecipeId(recipeId);
                    request.setAttribute("comments", comments);

                    double averageRating = ratingDao.calculateAverageRating(recipeId);
                    request.setAttribute("averageRating", averageRating);

                    UserService userService = new UserService();
                    User currentUser = userService.getUser(request, response);
                    if (currentUser != null && recipe.getUser().getId().equals(currentUser.getId())) {
                        request.setAttribute("user", currentUser);
                        request.setAttribute("isAuthor", true);
                    } else {
                        request.setAttribute("isAuthor", false);
                    }

                    // Перенаправление на JSP для отображения рецепта
                    request.getRequestDispatcher("/WEB-INF/views/showRecipe.jsp").forward(request, response);
                } else {
                    // Если рецепт не найден, отправить 404 ошибку
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Рецепт не найден");
                }
            } catch (NumberFormatException e) {
                // Некорректный формат ID рецепта
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный идентификатор рецепта");
            }
        } else {
            // Если путь не соответствует формату /recipe/{id}, отправить 404 ошибку
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Рецепт не найден");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        UserService userService = new UserService();
        User currentUser = userService.getUser(request, response);

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.matches("^/\\d+$")) {
            Long recipeId = Long.parseLong(pathInfo.substring(1));
            Recipe recipe = recipeDao.findById(recipeId);

            if (recipe != null && currentUser != null) {
                String action = request.getParameter("action");

                if ("delete".equals(action) && recipe.getUser().getId().equals(currentUser.getId())) {
                    try {
                        recipeDao.deleteRecipe(recipeId);
                        response.sendRedirect(request.getContextPath() + "/cookbook");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при удалении рецепта");
                    }
                } else if ("rate".equals(action)) {
                    double ratingValue = Integer.parseInt(request.getParameter("rating"));
                    Rating rating = new Rating();
                    rating.setRecipe(recipe);
                    rating.setUser(currentUser);
                    rating.setRating(ratingValue);

                    ratingDao.saveOrUpdateRating(rating);
                    response.sendRedirect(request.getContextPath() + "/recipe/" + recipeId);
                } else if ("addComment".equals(action)) {
                    // Добавление комментария
                    String commentText = request.getParameter("commentText");
                    Comment comment = new Comment();
                    comment.setRecipe(recipe);
                    comment.setUser(currentUser);
                    comment.setContent(commentText);

                    commentDao.save(comment);
                    response.sendRedirect(request.getContextPath() + "/recipe/" + recipeId);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Неверное действие");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный идентификатор рецепта");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный идентификатор рецепта");
        }
    }
}
