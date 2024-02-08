package com.travelgo.backend.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.travelgo.backend.domain.Area;
import com.travelgo.backend.domain.Location;
import com.travelgo.backend.domain.Picture;
import com.travelgo.backend.dto.LocationDTO;
import com.travelgo.backend.dto.Point;
import com.travelgo.backend.form.LocationForm;
import com.travelgo.backend.service.LocationService;
import com.travelgo.backend.service.PictureService;
import com.travelgo.backend.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @PostMapping(value = "")
    @Operation(summary = "지역 저장")
    @ApiResponse(responseCode = "200", description = "지역을 저장되고 s3에 이미지가 업로드 됩니다.")
    public ResponseEntity<?> saveLocation(@Valid @RequestPart(value = "form", required = false) LocationForm form,
                                          @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        Location location = Location.builder()
                .area(form.getArea())
                .hiddenFlag(false)
                .locationName(form.getLocationName())
                .locationImage(null)
                .longitude(form.getLongitude())
                .latitude(form.getLatitude())
                .description(form.getDescription()).build();

        locationService.createLocation(location);

        Picture savePicture = pictureService.saveLocationPicture(image, location);
        locationService.updateLocationPicture(location, savePicture);

        LocationDTO locationDTO = new LocationDTO(location);

        return ResponseEntity.ok().body(locationDTO);
    }

    @PatchMapping("/point/{locationId}")
    @Operation(summary = "지역 위도,경도 수정")
    public ResponseEntity<?> updateLocationPoint(@PathVariable Long locationId, @RequestParam Point point) {
        Location findLocation = locationService.findLocationById(locationId);
        locationService.changeLocationPoint(findLocation, point);

        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/{locationId}")
    @Operation(summary = "지역 히든스테이지 설정 수정")
    public ResponseEntity<?> setHiddenLocation(@PathVariable Long locationId, @RequestParam Point point) {
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

    @PostMapping("/getAreaLocations")
    @Operation(summary = "위치로부터 도를 찾아 해당 도에 있는 모든 위치 찾기")
    public ResponseEntity<?> getProvinceLocations(@RequestParam Double Latitude, @RequestParam Double Longitude) {
        // 위도, 경도로 도 찾기 (역지오코딩)
        String areaString = getAreaByLatLng(Latitude, Longitude);

        // 문자열을 Area 열거형으로 변환
        Area area = Area.valueOf(areaString);

        // 해당 도에 있는 모든 위치 찾기
        List<Location> locationList = locationService.findAllByArea(area);

        List<LocationDTO> locationDTOList = locationList.stream()
                .map(LocationDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(locationDTOList);
    }

    // 역지오코딩(위,경도 도 추출)
    public String getAreaByLatLng(Double latitude, Double longitude){
        String restApiKey = "4985bb6e1259e1eea63d88a7decc596b";
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + longitude + "&y=" + latitude + "&input_coord=WGS84";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + restApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        String jsonString = responseEntity.getBody();

        // 응답 JSON 도(area) 이름 추출
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        String area = jsonObject.get("documents").getAsJsonArray().get(0).getAsJsonObject().get("region_1depth_name").getAsString();

        return area;
    }

}
