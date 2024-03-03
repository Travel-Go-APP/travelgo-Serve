package com.travelgo.backend.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Area {

    서울("Seoul"),
    부산광역시("Busan"),
    대구광역시("Daegu"),
    인천광역시("Incheon"),
    광주광역시("Gwangju"),
    대전광역시("Daejeon"),
    울산광역시("Ulsan"),
    세종특별자치시("Sejong"),
    경기("Gyeonggido"),
    강원도("Gangwondo"),
    충청남도("Chungcheongnamdo"),
    충청북도("Chungcheongbukdo"),
    전라남도("Jeollanamdo"),
    전라북도("Jeollabukdo"),
    경상남도("Gyeonsangnamdo"),
    경상북도("Gyeonsangbukdo"),
    제주도("Jejudo");


    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String name;
}
