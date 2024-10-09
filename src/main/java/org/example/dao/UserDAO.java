package org.example.dao;


import org.example.entity.User;

import java.util.List;

public interface UserDAO {

    User save(User user);

    User findById(Long id);

    User findByEmail(String email);

    User findByUsername(String username);

    void delete(User user);

    void update(User user);

    List<User> findAll();

}

