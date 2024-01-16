package com.travelgo.backend.repository;

import com.travelgo.backend.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
