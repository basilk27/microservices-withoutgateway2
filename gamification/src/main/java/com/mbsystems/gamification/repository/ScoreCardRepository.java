package com.mbsystems.gamification.repository;

import com.mbsystems.gamification.domain.LeaderBoardRow;
import com.mbsystems.gamification.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

    @Query("SELECT SUM(s.score) FROM com.mbsystems.gamification.domain.ScoreCard s " +
            "                   WHERE s.userId = :userId GROUP BY s.userId")
    int getTotalScoreForUser( @Param( "userId" ) final Long userId );

    @Query("SELECT NEW com.mbsystems.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
            "FROM com.mbsystems.gamification.domain.ScoreCard s " +
            "GROUP BY s.userId ORDER BY SUM(s.score) DESC ")
    List<LeaderBoardRow > findFirst10();

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
