package com.travelgo.backend.controller;

import com.travelgo.backend.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pciture")
@Tag(name = "Picture", description = "사진 관련 API")
public class PictureController {
    private final PictureService pictureService;

    @DeleteMapping("/{id}")
    @Operation(summary = "사진 삭제")
    public ResponseEntity<?> deletePicture(@PathVariable Long id) {
        pictureService.delete(id);
        return ResponseEntity.ok().body(null);
    }
}
