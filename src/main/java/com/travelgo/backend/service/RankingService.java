package com.travelgo.backend.service;

import com.travelgo.backend.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    @Autowired
    public RankingService(RankingRepository rankingRepository){
        this.rankingRepository = rankingRepository;
    }
}
