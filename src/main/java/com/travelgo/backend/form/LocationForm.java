package com.travelgo.backend.form;

import com.travelgo.backend.domain.Area;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@NotEmpty
public class LocationForm {
    private Area area;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private String description;
}
