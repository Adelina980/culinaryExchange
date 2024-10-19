package org.example.controllers;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.util.DbException;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class login extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, String> errors = new HashMap<>();

        if (email == null || email.isEmpty()) {
            errors.put("email", "Пожалуйста, введите email.");
        }
        if (password == null || password.isEmpty()) {
            errors.put("password", "Пожалуйста, введите пароль.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("email", email);
            getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDao.findByEmail(email);
            if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
                errors.put("login", "Неверный email или пароль.");
                request.setAttribute("errors", errors);
                request.setAttribute("email", email);
                getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

            request.getSession().setAttribute("user", user);
            response.sendRedirect("/profile");
        } catch (DbException e) {
            e.printStackTrace();
            getServletContext().getRequestDispatcher("/WEB-INF/views/loginFailed.jsp").forward(request, response);
        }
    }

}
