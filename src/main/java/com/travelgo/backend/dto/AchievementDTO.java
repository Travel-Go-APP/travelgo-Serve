package com.travelgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDTO {
    private Long achievementId;
    private Long userId;
    private Long locationId;
    private Double achievementRate;
}
