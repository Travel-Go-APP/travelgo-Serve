package com.travelgo.backend.dto;

import lombok.Getter;

@Getter
public class CheckNicknameDTO {
    private String nickname;

    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}
