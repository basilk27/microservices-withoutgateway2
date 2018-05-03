package com.mbsystems.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.mbsystems.gamification.domain.Constants.DEFAULT_SCORE;
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "SCORE_CARD")
public class ScoreCard {

    @Id
    @GeneratedValue
    @Column(name = "CARD_ID")
    private final Long cardId;

    @Column(name = "USER_ID")
    private final Long userId;

    @Column(name = "ATTEMPT_ID")
    private final Long attemptId;

    @Column(name = "SCORE_TS")
    private final long scoreTimestamp;

    @Column(name = "SCORE")
    private final int score;

    public ScoreCard() {
        this(null, null, null, 0,0);
    }

    public ScoreCard( Long userId, Long attemptId ) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }
}
