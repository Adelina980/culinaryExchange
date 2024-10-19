package org.example.dao;

import org.example.entity.Feedback;

import java.util.List;

public interface FeedbackDao {

    Feedback save(Feedback feedback);

    List<Feedback> findAll();

    void delete(Feedback feedback);
}

