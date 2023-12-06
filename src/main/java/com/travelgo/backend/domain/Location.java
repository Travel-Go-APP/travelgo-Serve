package com.travelgo.backend.domain;

import lombok.*;
import java.util.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToMany(mappedBy = "location")
    private List<Review> reviews = new ArrayList<>();
    
    private boolean hiddenFlag; //히든 스테이지 설정

    private String locationName; //위치 이름
    
    private String locationImage; //장소 사진

    private Double latitude; //위도

    private Double longitude; //경도

    @Lob
    private String description; //설명

}
