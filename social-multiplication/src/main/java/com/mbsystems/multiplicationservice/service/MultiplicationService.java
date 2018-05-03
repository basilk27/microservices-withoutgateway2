package com.mbsystems.multiplicationservice.service;

import com.mbsystems.multiplicationservice.domain.Multiplication;
import com.mbsystems.multiplicationservice.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {
    Multiplication createRandomMultiplication();
    boolean checkAttempt( MultiplicationResultAttempt resultAttempt );
    List<MultiplicationResultAttempt> getStatsForUser( final String userAlias );
    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc( String userAlias);
    MultiplicationResultAttempt getResultById(final Long resultId);
}
