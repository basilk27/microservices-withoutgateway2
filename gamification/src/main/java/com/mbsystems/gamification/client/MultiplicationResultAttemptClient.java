package com.mbsystems.gamification.client;

import com.mbsystems.gamification.client.dto.MultiplicationResultAttempt;

public interface MultiplicationResultAttemptClient {
    MultiplicationResultAttempt retrieveMultiplicationResultAttemptById( final Long multiplicationId );
}
