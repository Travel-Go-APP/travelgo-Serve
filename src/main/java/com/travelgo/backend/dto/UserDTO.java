package com.travelgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String nickname;
    private String password;
    private String email;
    private int experience;
    private int workCount;
    private int detectionRange;
    private int level;
}
