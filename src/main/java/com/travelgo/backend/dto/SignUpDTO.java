package com.travelgo.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String kakaoId;

    private String nickname;

    //private String email;
}
