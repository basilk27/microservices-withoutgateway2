package com.mbsystems.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class GameStats {

    private final Long userId;
    private final int score;
    private final List<Badge> badgeList;

    public GameStats() {
        this.userId = 0L;
        this.score = 0;
        this.badgeList = new ArrayList<>();
    }

    public static GameStats emptyStats(final Long userId) {
        return new GameStats( userId, 0, Collections.emptyList() );
    }

    public List<Badge> getBadges() {
        return Collections.unmodifiableList( badgeList );
    }
}
