package org.example.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.dao.PreferenceDao;
import org.example.dao.UserDao;
import org.example.entity.Preference;
import org.example.entity.User;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;
import org.mindrot.jbcrypt.BCrypt;


@WebServlet("/register")
public class register extends HttpServlet {
    private UserDao userDao = new UserDao();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreferenceDao preferenceDao = new PreferenceDao();
        List<String> preferences = preferenceDao.getPreferences();
        request.setAttribute("preferences", preferences);
        getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String[] preferences = request.getParameterValues("preferences");

        Map<String, String> errors = new HashMap<>();

        if (name == null || name.isEmpty()) {
            errors.put("name", "Пожалуйста, введите имя.");
        }
        if (email == null || email.isEmpty()) {
            errors.put("email", "Пожалуйста, введите email.");
        }
        if (password == null || password.isEmpty() || passwordConfirm == null || passwordConfirm.isEmpty()) {
            errors.put("password", "Пожалуйста, введите пароль и его подтверждение.");
        }
        if (!password.equals(passwordConfirm)) {
            errors.put("passwordConfirm", "Пароли не совпадают.");
        }

        try {
            if (userDao.isUsernameExists(name)) {
                errors.put("name", "Пользователь с таким именем уже существует.");
            }
            if (userDao.isEmailExists(email)) {
                errors.put("email", "Пользователь с таким email уже существует.");
            }

            if (!errors.isEmpty()) {
                PreferenceDao preferenceDao = new PreferenceDao();
                List<String> preferencesList = preferenceDao.getPreferences();
                request.setAttribute("preferences", preferencesList);

                request.setAttribute("errors", errors);
                request.setAttribute("name", name);
                request.setAttribute("email", email);
                getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(hashedPassword);

            userDao.save(user);

            if (preferences != null && preferences.length > 0) {
                for (String preference : preferences) {
                    userDao.addPreferenceToUser(user, preference);
                }
            }
            response.sendRedirect("/login");
        } catch (DbException e) {
            e.printStackTrace();
            getServletContext().getRequestDispatcher("/WEB-INF/views/registerFailed.jsp")
                    .forward(request, response);
        }
    }

}

