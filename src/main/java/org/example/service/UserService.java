package org.example.service;

import org.example.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserService {
    public void auth(User user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
    }
    public boolean isNonAnonymous(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("user") != null;
    }

    public User getUser(HttpServletRequest req, HttpServletResponse res) {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            try {
                res.sendRedirect("/WEB-INF/views/login.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return user;
        }
    }
}
