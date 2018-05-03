package com.mbsystems.multiplicationservice.service;

import com.mbsystems.multiplicationservice.domain.Multiplication;
import com.mbsystems.multiplicationservice.domain.MultiplicationResultAttempt;
import com.mbsystems.multiplicationservice.domain.User;
import com.mbsystems.multiplicationservice.event.EventDispatcher;
import com.mbsystems.multiplicationservice.event.MultiplicationSolvedEvent;
import com.mbsystems.multiplicationservice.repository.MultiplicationResultAttemptRepository;
import com.mbsystems.multiplicationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
    private UserRepository userRepository;
    private EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl( final RandomGeneratorService randomGeneratorService,
                                      final MultiplicationResultAttemptRepository multiplicationResultAttemptRepository,
                                      final UserRepository userRepository,
                                      final EventDispatcher eventDispatcher ) {
        this.randomGeneratorService = randomGeneratorService;
        this.multiplicationResultAttemptRepository = multiplicationResultAttemptRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();

        return new Multiplication( factorA, factorB );
    }

    @Override
    @Transactional
    public boolean checkAttempt( MultiplicationResultAttempt resultAttempt ) {
        Assert.isTrue( !resultAttempt.isCorrect(), "You can't send attempt marked as correct!!" );

        //check if the user already exists for that alias
        Optional<User > optionalUser = userRepository.findByAlias( resultAttempt.getUser().getAlias() );

        boolean correct = resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() *
                        resultAttempt.getMultiplication().getFactorB();

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt( optionalUser.orElse( resultAttempt.getUser() ),
                resultAttempt.getMultiplication(),
                resultAttempt.getResultAttempt(), correct );

        //Store the attempt
        multiplicationResultAttemptRepository.save( checkedAttempt );

        //Communicate the result via Event
        eventDispatcher.send( new MultiplicationSolvedEvent( checkedAttempt.getId(),
                checkedAttempt.getUser().getId(),
                checkedAttempt.isCorrect() ) );

        return correct;
    }

    @Override
    public List< MultiplicationResultAttempt > getStatsForUser( String userAlias ) {
        return multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc( userAlias );
    }

    @Override
    public List< MultiplicationResultAttempt > findTop5ByUserAliasOrderByIdDesc( String userAlias ) {
        return multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc( userAlias );
    }

    @Override
    public MultiplicationResultAttempt getResultById(final Long resultId) {
        return multiplicationResultAttemptRepository.findById(resultId)
                .orElseThrow( () -> new IllegalArgumentException(
                        "The request result [" + resultId + "] does not exist."
                ) );
    }
}
