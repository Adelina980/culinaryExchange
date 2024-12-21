package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.User;


public class UserService {
    public void auth(User user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
    }

    public boolean isNonAnonymous(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("user") != null;
    }

    public User getUser(HttpServletRequest req, HttpServletResponse res) {
        User user = (User) req.getSession().getAttribute("user");
        return user;
    }
}
