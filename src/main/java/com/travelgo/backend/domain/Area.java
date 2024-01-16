package com.travelgo.backend.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Area {

    Seoul("서울특별시"),
    Busan("부산광역시"),
    Daegu("대구광역시"),
    Incheon("인천광역시"),
    Gwangju("광주광역시"),
    Daejeon("대전광역시"),
    Ulsan("울산광역시"),
    Sejong("세종특별자치시"),
    Gyeonggido("경기도"),
    Gangwondo("강원도"),
    Chungcheongnamdo("충청남도"),
    Chungcheongbukdo("충청북도"),
    Jeollanamdo("전라남도"),
    Jeollabukdo("전라북도"),
    Gyeonsangnamdo("경상남도"),
    Gyeonsangbukdo("경상북도"),
    Jejudo("제주도");


    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String name;
}
