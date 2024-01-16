package com.travelgo.backend.dto;

import com.travelgo.backend.domain.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Long locationId;
    private Area area;
    private List<Long> reviewIds;
    private boolean hiddenFlag;
    private String locationName;
    private String locationImage;
    private Double latitude;
    private Double longitude;
    private String description;
}
