package com.travelgo.backend.dto;

import com.travelgo.backend.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개자수로 하는 생성자
public class VisitDTO {
    private Long visitId;
    private Long userId;
    private Long locationId;
    // userId, locationId 같은 경우에는 객체 ID만 이용한다
    // 도메인 객체를 직접 포함시키지 않고, 객체 식별자만 포함한다
    private LocalDateTime visitTime;

    public VisitDTO(Visit visit) {
        this.visitId = visit.getVisitId();
        this.userId = visit.getUser().getUserId();
        this.locationId = visit.getLocation().getLocationId();
        this.visitTime = visit.getVisitTime();
    }
}
