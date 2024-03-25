package com.travelgo.backend.dto;

import com.travelgo.backend.domain.Item;
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

    public ItemDTO(Item item) {
        this.itemId = item.getItemId();
        this.itemImage = item.getItemImage();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
    }
}
