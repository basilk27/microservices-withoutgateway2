package com.mbsystems.gamification.client;

import com.mbsystems.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

    private final RestTemplate restTemplate;
    private final String muliplicationHost;

    @Autowired
    public MultiplicationResultAttemptClientImpl( final RestTemplate restTemplate,
                                                  @Value("${multiplicationHost}") final String muliplicationHost ) {
        this.restTemplate = restTemplate;
        this.muliplicationHost = muliplicationHost;
    }
    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById( Long multiplicationAttempId ) {
        return restTemplate.getForObject( muliplicationHost + "/results/" +
                multiplicationAttempId, MultiplicationResultAttempt.class );
    }
}
