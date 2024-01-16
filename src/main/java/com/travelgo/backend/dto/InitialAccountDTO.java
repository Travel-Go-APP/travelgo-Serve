package com.travelgo.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialAccountDTO {
    private String kakaoId;

    private String email;
}
