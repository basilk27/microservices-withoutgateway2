package com.mbsystems.gamification.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class MultiplicationSolvedEvent implements Serializable {

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

    public MultiplicationSolvedEvent() {
        this.multiplicationResultAttemptId = -1L;
        this.userId = -1L;
        this.correct = false;
    }
}
