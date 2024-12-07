package org.example.controllers;

import org.example.dao.*;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@WebServlet("/recipe/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

public class createRecipe extends HttpServlet {
    private RecipeDao recipeDao;
    private PreferenceDao preferenceDao;
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recipeDao = (RecipeDao) getServletContext().getAttribute("recipeDao");
        preferenceDao = (PreferenceDao) getServletContext().getAttribute("preferenceDao");
        userService = (UserService) getServletContext().getAttribute("userService");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);
        getServletContext().getRequestDispatcher("/WEB-INF/views/createRecipe.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");


        User user = userService.getUser(request, response);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String ingredients = request.getParameter("ingredients");
        String steps = request.getParameter("steps");
        int cookingTime = Integer.parseInt(request.getParameter("cookingTime"));
        int servings = Integer.parseInt(request.getParameter("servings"));
        //        Part imagePart = request.getPart("image");
        String preference = request.getParameter("preference");


        // Проверка, загружено ли изображение
        //        byte[] imageData = null;
        //        if (imagePart.getSize() > 0) {
        //            imageData = convertPartToByteArray(imagePart);
        //        }

        try {
            Recipe recipe = new Recipe();
            recipe.setName(title);
            recipe.setDescription(description);
            recipe.setCategory(preference);
            recipe.setPreparationTime(cookingTime);
            recipe.setServings(servings);
            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);
            //        recipe.setImage(ima);
            //            recipe.setUser(user);
            recipeDao.saveRecipe(recipe, user);
            response.sendRedirect(getServletContext().getContextPath()+"/cookbook");
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок
            request.setAttribute("errorMessage", "Ошибка при сохранении рецепта: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/createRecipe.jsp").forward(request, response);
        }


    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 1).trim().replaceAll("\"", "");
            }
        }
        return "";
    }

    private void saveFile(String filePath, InputStream inputStream) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();

    }
}
