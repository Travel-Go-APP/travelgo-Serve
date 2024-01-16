package com.travelgo.backend.domain;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    normal("노말"),
    rare("희귀"),
    Legend("전설"),
    Special("특수");

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String grade;
}