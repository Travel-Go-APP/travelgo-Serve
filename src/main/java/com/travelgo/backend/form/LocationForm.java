package com.travelgo.backend.form;

import com.travelgo.backend.domain.Area;
import lombok.Data;

import java.util.List;

@Data
public class LocationForm {
    private Area area;
    private boolean hiddenFlag;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private String description;
}
