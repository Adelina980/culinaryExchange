//package org.example.controllers;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.example.dao.UserDao;
//import org.example.entity.User;
//import org.example.service.EmailService;
//import org.example.util.DbException;
//import org.mindrot.jbcrypt.BCrypt;
//
//import java.io.IOException;
//import java.util.UUID;
//
//
//// Сервлет для отправки письма с подтверждением смены пароля
//@WebServlet("/forgotPassword")
//public class forgotPassword extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        getServletContext().getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String email = request.getParameter("email");
//        String newPassword = request.getParameter("newPassword");
//        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
//        UserDao userDao = new UserDao();
//        // Проверка, существует ли пользователь с таким email
//        User user = null;
//        try {
//            user = userDao.findByEmail(email);
//        } catch (DbException e) {
//            throw new RuntimeException(e);
//        }
//        if (user != null) {
//            // Генерируем уникальный токен подтверждения
//            String confirmationToken = UUID.randomUUID().toString();
//
//            // Сохраняем токен в базе данных для этого пользователя
//            user.setConfirmationToken(confirmationToken);
//            try {
//                userDao.updateUser(user);
//            } catch (DbException e) {
//                throw new RuntimeException(e);
//            }
//
//            request.getSession().setAttribute("hashedPassword", hashedPassword);
//
//            // Формируем ссылку подтверждения
//            String confirmationLink = getServletContext().getContextPath() + "/confirmResetPassword?token=" + confirmationToken;
//
//            EmailService emailService = new EmailService();
//            // Отправляем письмо с ссылкой
//            emailService.sendEmail(user.getEmail(), "Подтверждение смены пароля", "Пожалуйста, подтвердите смену пароля, перейдя по ссылке: " + confirmationLink);
//
//            response.sendRedirect("/WEB-INF/views/waitingPage.jsp");
//        } else {
//            request.setAttribute("errorMessage", "Пользователь с таким адресом электронной почты не найден.");
//            getServletContext().getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
//        }
//    }
//}