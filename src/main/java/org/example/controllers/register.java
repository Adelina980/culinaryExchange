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
import org.mindrot.jbcrypt.BCrypt;


@WebServlet("/register")
public class register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String[] preferences = request.getParameterValues("preferences");

        if (name == null || name.isEmpty() || email == null || email.isEmpty() ||
                password == null || password.isEmpty() || passwordConfirm == null || passwordConfirm.isEmpty()) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Ошибка регистрации</title></head><body>");
            out.println("<h2>Ошибка регистрации</h2>");
            out.println("<p>Пожалуйста, заполните все поля формы.</p>");
            out.println("<a href='/register'>Вернуться к регистрации</a>");
            out.println("</body></html>");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Ошибка регистрации</title></head><body>");
            out.println("<h2>Ошибка регистрации</h2>");
            out.println("<p>Пароли не совпадают. Пожалуйста, проверьте введенные данные.</p>");
            out.println("<a href='/register'>Вернуться к регистрации</a>");
            out.println("</body></html>");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/culinaryExchange",
                "root",
                "123");
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (name, email, password, preferences) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, hashedPassword);
            statement.setString(4, String.join(",",preferences));

            statement.executeUpdate();

            response.sendRedirect("/login");
        } catch (SQLException e) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/registerFailed.jsp").forward(request,response);
        }
    }
}
