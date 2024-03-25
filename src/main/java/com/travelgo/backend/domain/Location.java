package com.travelgo.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.travelgo.backend.dto.LocationDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToMany(mappedBy = "location")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "location", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Picture picture;

    private boolean hiddenFlag; //히든 스테이지 설정

    private String locationName; //위치 이름

    private String locationImage; //장소 사진

    private Double latitude; //위도

    private Double longitude; //경도

    @Lob
    private String description; //설명

    public Location(Area area, String locationName, Double latitude, Double longitude, String description) {
        this.area = area;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    //메서드
    public void changeLocationImage(String imageUrl) {
        this.locationImage = imageUrl;
    }

    public void changePoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Visit> visits = new ArrayList<>();

    public void setHiddenFlag(){
        this.hiddenFlag = true;
    }
  
}
