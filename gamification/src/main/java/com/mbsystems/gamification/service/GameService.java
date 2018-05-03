package com.mbsystems.gamification.service;

import com.mbsystems.gamification.client.dto.MultiplicationResultAttempt;
import com.mbsystems.gamification.domain.GameStats;
import com.mbsystems.gamification.domain.ScoreCard;

import java.util.List;

public interface GameService {

    GameStats newAttemptForUser( Long userId, Long attempId, boolean correct);
    GameStats retriveStatsForUser(Long userId);
    int getTotalScoreForUser( Long userId );
    List<ScoreCard > findByUserIdOrderByScoreTimestampDesc( final Long userId);
    MultiplicationResultAttempt retrieveMultiplicationResultAttemptById( final Long multiplicationId );
}
