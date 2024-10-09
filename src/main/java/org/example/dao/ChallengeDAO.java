package org.example.dao;

import org.example.entity.Challenge;

import java.util.List;

public interface ChallengeDAO {

    Challenge save(Challenge challenge);

    List<Challenge> findAll();

    Challenge findById(Long id);

    List<Challenge> findActiveChallenges();

    void delete(Challenge challenge);

    void update(Challenge challenge);
}

