package org.example.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PreferenceDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserPreferenceDao;
import org.example.entity.Recipe;

import java.io.IOException;
import java.util.List;

@WebServlet("/main")
public class MainPage extends HttpServlet {
    private RecipeDao recipeDao;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recipeDao = (RecipeDao) getServletContext().getAttribute("recipeDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Recipe> recipes = recipeDao.findAllRecipes();
        req.setAttribute("recipes", recipes);
        req.getRequestDispatcher("/WEB-INF/views/mainPage.jsp").forward(req, resp);
    }
}
