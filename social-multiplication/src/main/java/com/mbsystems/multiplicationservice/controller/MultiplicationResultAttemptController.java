package com.mbsystems.multiplicationservice.controller;

import com.mbsystems.multiplicationservice.domain.MultiplicationResultAttempt;
import com.mbsystems.multiplicationservice.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {

    private MultiplicationService multiplicationService;

    @Autowired
    public MultiplicationResultAttemptController( MultiplicationService multiplicationService ) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt > postResult( @RequestBody MultiplicationResultAttempt resultAttempt ) {
        boolean isCorrect = multiplicationService.checkAttempt( resultAttempt );
        return ResponseEntity.ok( new MultiplicationResultAttempt( resultAttempt.getUser(),
                resultAttempt.getMultiplication(),
                resultAttempt.getResultAttempt(),
                isCorrect) );
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt> > getStatistics( @RequestParam("alias") String alias) {
        return ResponseEntity.ok(multiplicationService.findTop5ByUserAliasOrderByIdDesc( alias ));
    }

    @GetMapping("/{resultId}")
    ResponseEntity<MultiplicationResultAttempt> getResultById(final @PathVariable("resultId") Long resultId) {
        return ResponseEntity.ok(multiplicationService.getResultById(resultId));
    }
}
