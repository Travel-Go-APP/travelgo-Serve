package com.travelgo.backend.dto;

import com.travelgo.backend.domain.Area;
import com.travelgo.backend.domain.Location;
import com.travelgo.backend.domain.Picture;
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
    private boolean hiddenFlag;
    private String locationName;
    private String locationImage;
    private Double latitude;
    private Double longitude;
    private String description;

    public LocationDTO(Location location) {
        this.locationId = location.getLocationId();
        this.area = location.getArea();
        this.hiddenFlag = location.isHiddenFlag();
        this.locationName = location.getLocationName();
        this.locationImage = location.getLocationImage();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.description = location.getDescription();
    }
}
