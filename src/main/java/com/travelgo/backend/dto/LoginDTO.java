package com.travelgo.backend.dto;

import com.travelgo.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private Long userId;
    private int detectionRange;
    private String email;
    private int experience;
    private String kakaoId;
    private int level;
    private String nickname;
    private int workCount;

    public LoginDTO(User user) {
        this.userId = user.getUserId();
        this.detectionRange = user.getDetectionRange();
        this.email = user.getEmail();
        this.experience = user.getExperience();
        this.kakaoId = user.getKakaoId();
        this.level = user.getLevel();
        this.nickname = user.getNickname();
        this.workCount = user.getWorkCount();
    }
}
