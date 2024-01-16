package com.travelgo.backend.controller;

import com.travelgo.backend.domain.Location;
import com.travelgo.backend.domain.Picture;
import com.travelgo.backend.dto.LocationDTO;
import com.travelgo.backend.dto.Point;
import com.travelgo.backend.form.LocationForm;
import com.travelgo.backend.service.LocationService;
import com.travelgo.backend.service.PictureService;
import com.travelgo.backend.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@Tag(name = "Location", description = "장소 관련 API")
public class LocationController {
    private final LocationService locationService;
    private final S3UploadService s3UploadService;
    private final PictureService pictureService;

    @PostMapping("")
    @Operation(summary = "지역 저장")
    public ResponseEntity<?> saveLocation(@RequestBody LocationForm form,
                                          @RequestPart(required = false, value = "image") MultipartFile image) throws IOException {
        Location location = new Location(form.getArea(), form.getLocationName(), form.getLatitude(), form.getLongitude(), form.getDescription());
        locationService.createLocation(location);

        Picture savePicture = pictureService.saveLocationPicture(image, location);
        location.saveLocationImage(savePicture.getImageUrl());

        LocationDTO locationDTO = new LocationDTO(location);

        return ResponseEntity.ok().body(locationDTO);
    }

    @PatchMapping("/{locationId}")
    @Operation(summary = "지역 위도,경도 수정")
    public ResponseEntity<?> updateLocationPoint(@PathVariable Long locationId, @RequestParam Point point) {
        Location findLocation = locationService.findLocationById(locationId);
        locationService.changeLocationPoint(findLocation, point);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{locationId}")
    @Operation(summary = "지역 삭제")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        Picture picture = pictureService.findLocationPicture(locationId);
        s3UploadService.fileDelete(picture.getImageUrl());

        Location findLocation = locationService.findLocationById(locationId);
        locationService.deleteLocation(findLocation);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("")
    @Operation(summary = "Id로 지역 찾기")
    public ResponseEntity<?> getLocationById(@RequestParam Long locationId) {
        Location findLocation = locationService.findLocationById(locationId);
        LocationDTO locationDTO = new LocationDTO(findLocation);

        return ResponseEntity.ok().body(locationDTO);
    }

    @GetMapping("/findAllLocation")
    @Operation(summary = "전체 지역 찾기")
    public ResponseEntity<?> getLocation(@RequestParam Long locationId) {
        List<Location> locationList = locationService.findAll();

        List<LocationDTO> locationDTOList = locationList.stream()
                .map(LocationDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(locationDTOList);
    }

    @GetMapping("/findlocationByname")
    @Operation(summary = "이름으로 지역 찾기")
    public ResponseEntity<?> getLocationByName(@RequestParam String name) {
        Location findLocation = locationService.findLocationByName(name);
        LocationDTO locationDTO = new LocationDTO(findLocation);

        return ResponseEntity.ok().body(locationDTO);
    }


}
