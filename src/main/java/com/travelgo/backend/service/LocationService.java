package com.travelgo.backend.service;

import com.travelgo.backend.domain.Location;
import com.travelgo.backend.dto.Point;
import com.travelgo.backend.form.LocationForm;
import com.travelgo.backend.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;

    public Long createLocation(LocationForm form) {
        Location location = Location.builder()
                .area(form.getArea())
                .hiddenFlag(false)
                .locationName(form.getLocationName())
                .locationImage(null)
                .longitude(form.getLongitude())
                .latitude(form.getLatitude())
                .description(form.getDescription()).build();
        locationRepository.save(location);
        return location.getLocationId();
    }

    public void deleteLocation(Location location) {
        locationRepository.delete(location);
    }

    public Location findLocationById(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(() -> new NotFoundException(locationId + "가 존재하지 않습니다."));
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location findLocationByName(String locationName) {
        return locationRepository.findByLocationName(locationName);
    }

    @Transactional
    public void changeLocationPoint(Location location, Point point) {
        location.changePoint(point.getLatitude(), point.getLongitude());
    }
}
