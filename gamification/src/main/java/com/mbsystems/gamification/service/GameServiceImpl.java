package com.mbsystems.gamification.service;

import com.mbsystems.gamification.client.MultiplicationResultAttemptClient;
import com.mbsystems.gamification.client.dto.MultiplicationResultAttempt;
import com.mbsystems.gamification.domain.Badge;
import com.mbsystems.gamification.domain.BadgeCard;
import com.mbsystems.gamification.domain.GameStats;
import com.mbsystems.gamification.domain.ScoreCard;
import com.mbsystems.gamification.repository.BadgeCardRepository;
import com.mbsystems.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mbsystems.gamification.domain.Constants.LUCKY_NUMBER;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;
    private MultiplicationResultAttemptClient attemptClient;

    @Autowired
    public GameServiceImpl( ScoreCardRepository scoreCardRepository,
                            BadgeCardRepository badgeCardRepository,
                            MultiplicationResultAttemptClient attemptClient) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
        this.attemptClient = attemptClient;
    }

    @Override
    public GameStats newAttemptForUser( final Long userId, final Long attempId, final boolean correct ) {
        //For the first version we 'll give points only if it's correct
        if( correct ) {
            ScoreCard scoreCard = new ScoreCard( userId, attempId );

            scoreCardRepository.save( scoreCard );

            log.info( "User with id {} points for attempt id {}", userId, scoreCard.getScore() );

            List<BadgeCard> badgeCardList = processForBadges( userId, attempId );

            return new GameStats( userId, scoreCard.getScore(),
                    badgeCardList.stream().map( BadgeCard::getBadge )
                            .collect( Collectors.toList() ) );
        }

        return GameStats.emptyStats( userId );
    }

    private List<BadgeCard> processForBadges(final Long userId, final Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalScoreForUser( userId );

        log.info( "New score for user {} is {}", userId, totalScore );

        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc( userId );

        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc( userId );

        checkAndGivenBageBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
                .ifPresent( badgeCards::add );

        checkAndGivenBageBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
                .ifPresent( badgeCards::add );

        checkAndGivenBageBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
                .ifPresent( badgeCards::add );

        if ( scoreCardList.size() == 1 &&
                !containsBadge( badgeCardList, Badge.FIRST_WIN )) {
            BadgeCard firstWonBage = giveBadgeToUser( Badge.FIRST_WIN, userId );
            badgeCards.add( firstWonBage );
        }

        log.info( "##BMK 1- Lucky number badge:  retrieveMultiplicationResultAttemptById" );
        // Lucky number badge
        MultiplicationResultAttempt attempt = attemptClient
                .retrieveMultiplicationResultAttemptById( attemptId );

        if(!containsBadge(badgeCardList, Badge.LUCKY_NUMBER) &&
                (LUCKY_NUMBER == attempt.getMultiplicationFactorA() ||
                        LUCKY_NUMBER == attempt.getMultiplicationFactorB())) {
            BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
            badgeCards.add(luckyNumberBadge);
        }

        return badgeCards;
    }

    @Override
    public GameStats retriveStatsForUser( final Long userId ) {
        int score = scoreCardRepository.getTotalScoreForUser( userId );

        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc( userId );

        return new GameStats(userId, score, badgeCardList.stream().map( BadgeCard::getBadge )
                .collect( Collectors.toList()) );
    }

    @Override
    public int getTotalScoreForUser( Long userId ) {
        return scoreCardRepository.getTotalScoreForUser(userId);
    }

    @Override
    public List< ScoreCard > findByUserIdOrderByScoreTimestampDesc( Long userId ) {
        return scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
    }

    //    @Override
    //    public List< BadgeCard > findByUserIdOrderByBadgeTimestampDesc( Long userId ) {
    //        return badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
    //    }

    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById( Long multiplicationId ) {
        return attemptClient.retrieveMultiplicationResultAttemptById(multiplicationId);
    }

    private Optional<BadgeCard > checkAndGivenBageBasedOnScore( final List<BadgeCard> badgeCardList, final Badge badge,
                                                                final int score, final int scoreThreshold,
                                                                final Long userId) {
        if ( score >= scoreThreshold && !containsBadge(badgeCardList, badge)) {
            return Optional.of( giveBadgeToUser(badge, userId) );
        }

        return Optional.empty();
    }

    private boolean containsBadge(final List<BadgeCard> badgeCardList, final Badge badge) {
        return badgeCardList.stream().anyMatch( b -> b.getBadge().equals( badge ) );
    }

    private BadgeCard giveBadgeToUser(final Badge badge, Long userId) {
        BadgeCard badgeCard = new BadgeCard( userId, badge );

        badgeCardRepository.save( badgeCard );

        log.info( "User with id {} won a new badge: {}", userId, badge );

        return badgeCard;
    }
}
