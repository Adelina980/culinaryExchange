package org.example.dao;

import org.example.entity.Feedback;

import java.util.List;

public interface FeedbackDAO {

    Feedback save(Feedback feedback);

    List<Feedback> findAll();

    void delete(Feedback feedback);
}

