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

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "BADGE_CARD")
public class BadgeCard {

    @Id
    @GeneratedValue
    @Column(name = "BADGE_ID")
    private final Long badgeId;
    private final Long userId;
    private final long badgeTimestamp;
    private final Badge badge;

    //Empty constructor for JSON / JPA
    public BadgeCard() {
        this(null, null, 0, null);
    }

    public BadgeCard( final Long userId, final Badge badge ) {
        this(null, userId, System.currentTimeMillis(), badge);
    }
}
