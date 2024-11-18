package org.example.controllers;

import org.example.dao.*;
import org.example.entity.Preference;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.entity.UserPreference;
import org.example.service.UserService;
import org.example.util.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/profile/edit")
public class editProfile extends HttpServlet {
    private final UserDao userDao = new UserDao();
    private final UserPreferenceDao userPreferenceDao = new UserPreferenceDao();
    private final PreferenceDao preferenceDao = new PreferenceDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);

        UserService userService = new UserService();
        User currentUser = userService.getUser(request, response);
        UserDao userDao = new UserDao();
        User user = userDao.findById(currentUser.getId());

        if (user != null) {
            String previousPage = request.getHeader("Referer");
            if (previousPage != null) {
                request.getSession().setAttribute("previousPage", previousPage);
            }

            String createdAt = user.getCreatedAt();
            RatingDao ratingDao = new RatingDao();
            double userRating = ratingDao.calculateUserAverageRating(user.getId());

            UserPreferenceDao userPreferenceDao = new UserPreferenceDao();
            List<String> category = null;
            try {
                category = userPreferenceDao.getPreferencesByUserId(user.getId());
            } catch (DbException e) {
                throw new RuntimeException(e);
            }



            request.setAttribute("user", user);
            request.setAttribute("createdAt", createdAt);
            request.setAttribute("userRating", userRating);
            request.setAttribute("category", category);
            request.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Вы не можете редактировать этот рецепт.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String userId = request.getParameter("userId");
//        Blob avatar = request.getParameter("avatar");
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String[] preferences = request.getParameterValues("preferences");

        User updatedUser = new User();
        updatedUser.setId(Long.parseLong(userId));
//        updatedUser.setAvatar(avatar);
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);

        userDao.updateUser(updatedUser);
        UserPreferenceDao userPreferenceDao = new UserPreferenceDao();
        try {
            userPreferenceDao.deletePreferences(updatedUser.getId());
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        if (preferences != null && preferences.length > 0) {
            for (String preference : preferences) {
                try {
                    userDao.addPreferenceToUser(updatedUser, preference);
                } catch (DbException e) {
                    throw new RuntimeException(e);
                }

            }
        }


//        List<Preference> newPreferences = new ArrayList<>();
//        if (selectedPreferences != null) {
//            for (String preferenceName : selectedPreferences) {
//                Preference preference = preferenceDao.findByPreferenceName(preferenceName);
//                if (preference != null) {
//                    newPreferences.add(preference);
//                }
//            }
//        }
//        try {
//            userPreferenceDao.setPreferences(updatedUser.getId(), newPreferences);
//        } catch (DbException e) {
//            throw new RuntimeException(e);
//        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
