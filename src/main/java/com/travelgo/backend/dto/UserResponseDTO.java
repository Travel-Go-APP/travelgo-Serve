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

    public UserResponseDTO(User user) {
        this.kakaoId = user.getKakaoId();
        this.level = user.getLevel();
        this.experience = user.getExperience();
        this.workCount = user.getWorkCount();
    }
}
