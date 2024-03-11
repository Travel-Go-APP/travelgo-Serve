package com.travelgo.backend.controller;

import com.travelgo.backend.domain.Location;
import com.travelgo.backend.domain.User;
import com.travelgo.backend.domain.Visit;
import com.travelgo.backend.dto.VisitDTO;
import com.travelgo.backend.service.LocationService;
import com.travelgo.backend.service.UserService;
import com.travelgo.backend.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
@Tag(name = "Visit", description = "방문 관련 API")
public class VisitController {

    private final UserService userService;
    private final LocationService locationService;
    private final VisitService visitService;

    @PostMapping("/visit")
    @Operation(summary = "유저 명소 방문 이벤트")
    public ResponseEntity<?> visitLocation(@RequestParam Long userId, @RequestParam Long locationId){
        User user = userService.findUserById(userId);
        Location location = locationService.findLocationById(locationId);

        if(user == null || location == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 혹은 명소를 찾을 수 없습니다.");
        }

        Visit visit = Visit.builder().user(user)
                .location(location)
                .visitTime(LocalDateTime.now())
                .build();

        visitService.createVisit(visit);

        VisitDTO visitDTO = new VisitDTO(visit);

        return ResponseEntity.ok().body(visitDTO);
    }
}
