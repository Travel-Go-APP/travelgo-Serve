package com.travelgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDropDTO {
    private Long dropId;
    private Long userId;
    private Long locationId;
    private Long itemId;
    private LocalDateTime dropTime;
}
