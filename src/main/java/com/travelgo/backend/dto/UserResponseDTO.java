package com.travelgo.backend.dto;

import com.travelgo.backend.domain.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String kakaoId;
    private int level;
    private int experience;
    private int workCount;
    private boolean levelUp;
    //private double experienceRatio; // 경험치 비율
    private int totalExperience;

    public UserResponseDTO(User user, boolean levelUp, int nextLevelExp) {
        this.kakaoId = user.getKakaoId();
        this.level = user.getLevel();
        this.experience = user.getExperience();
        this.workCount = user.getWorkCount();
        this.levelUp = levelUp;
        this.totalExperience = nextLevelExp;
        //this.experienceRatio = ((double) user.getExperience())/nextLevelExp; // 경험치 비율 계산 및 반환
    }
}
