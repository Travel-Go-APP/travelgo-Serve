package com.travelgo.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

    private String placeName; //장소 이름

    private String title;//장소 제목

    private String location;//장소 위치

    @Lob
    private String explanation;

    @Enumerated(EnumType.STRING)
    private Checking checking; //방문 확인

    private Double latitude; //위도
    private Double longitude; //경도
}
