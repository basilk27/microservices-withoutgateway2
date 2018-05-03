package com.mbsystems.gamification.service;

import com.mbsystems.gamification.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {
    List<LeaderBoardRow > getCurrentLeaderBoard();
}
