package com.travelgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long itemId;
    private String itemImage;
    private String itemName;
    private String itemDescription;
}
