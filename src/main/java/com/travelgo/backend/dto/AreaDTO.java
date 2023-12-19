package com.travelgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {
    private String name;

    // Area area = Area.Seoul;
    // AreaDTO areaDTO = new AreaDTO(area.getName());
    // 같은 각 요소를 String 형태로 변환해서 DTO 사용 가능
}
