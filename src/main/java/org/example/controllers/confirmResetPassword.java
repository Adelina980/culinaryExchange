//package org.example.controllers;
//
//import org.example.dao.UserDao;
//import org.example.entity.User;
//import org.example.util.DbException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/confirmResetPassword")
//public class confirmResetPassword extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String token = request.getParameter("token");
//        UserDao userDao = new UserDao();
//
//        // Проверка, существует ли пользователь с таким токеном
//        User user = null;
//        try {
//            user = userDao.getUserByConfirmationToken(token);
//        } catch (DbException e) {
//            throw new RuntimeException(e);
//        }
//        if (user != null) {
//            String hashedPassword = (String) request.getSession().getAttribute("hashedPassword");
//            if (hashedPassword != null) {
//                // Обновляем пароль пользователя в базе данных
//                user.setPassword(hashedPassword);
//                try {
//                    userDao.updateUser(user);
//                } catch (DbException e) {
//                    throw new RuntimeException(e);
//                }
//
//                // Удаляем токен подтверждения из базы данных
//                user.setConfirmationToken(null);
//                try {
//                    userDao.updateUser(user);
//                } catch (DbException e) {
//                    throw new RuntimeException(e);
//                }
//                request.getSession().removeAttribute("hashedPassword");
//                // Перенаправляем на страницу входа
//                response.sendRedirect("/login.");
//            } else {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка: Хэшированный пароль не найден.");
//            }
//        } else {
//            // Пользователь с таким токеном не найден
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка: Пользователь не найден.");
//        }
//    }
//}
