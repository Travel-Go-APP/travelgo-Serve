package com.travelgo.backend.service;

import com.travelgo.backend.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository){
        this.achievementRepository = achievementRepository;
    }
}
