package com.travelgo.backend.domain;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    노말("Normal", 0.5),
    레어("Rare",0.3),
    전설("Legend",0.15),
    스페셜("Special",0.05);

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String grade;
    private final Double rate;
}