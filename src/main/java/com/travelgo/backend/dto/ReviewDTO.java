package com.travelgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private Long locationId;
    private Long userId;
    private Float score;
    private String reviewContent;
    private LocalDateTime reviewTime;
}
