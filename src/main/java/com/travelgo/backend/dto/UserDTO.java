package com.travelgo.backend.dto;

import com.travelgo.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String kakaoId;
    private String nickname;
    //private String password;
    private String email;
    private int experience;
    private int workCount;
    private int detectionRange;
    private int level;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.kakaoId = user.getKakaoId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.experience = user.getExperience();
        this.workCount = user.getWorkCount();
        this.detectionRange = user.getDetectionRange();
        this.level = user.getLevel();
    }
}
