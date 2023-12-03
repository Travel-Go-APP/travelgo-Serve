package com.travelgo.backend.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Checking {
    Visited("방문"),
    Unvisited("미방문");

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String name;
}
