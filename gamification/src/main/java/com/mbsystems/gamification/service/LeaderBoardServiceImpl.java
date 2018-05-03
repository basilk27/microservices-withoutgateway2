package com.mbsystems.gamification.service;

import com.mbsystems.gamification.domain.LeaderBoardRow;
import com.mbsystems.gamification.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private ScoreCardRepository scoreCardRepository;

    LeaderBoardServiceImpl( ScoreCardRepository scoreCardRepository ) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public List< LeaderBoardRow > getCurrentLeaderBoard() {
        return scoreCardRepository.findFirst10();
    }
}

