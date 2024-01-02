package com.travelgo.backend.dto;

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
}
