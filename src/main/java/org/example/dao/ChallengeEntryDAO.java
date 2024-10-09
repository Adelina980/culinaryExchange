package org.example.dao;

import org.example.entity.ChallengeEntry;

import java.util.List;

public interface ChallengeEntryDAO {

    ChallengeEntry save(ChallengeEntry challengeEntry);

    List<ChallengeEntry> findByChallengeId(Long challengeId);

    List<ChallengeEntry> findByUserId(Long userId);

    void delete(ChallengeEntry challengeEntry);
}

