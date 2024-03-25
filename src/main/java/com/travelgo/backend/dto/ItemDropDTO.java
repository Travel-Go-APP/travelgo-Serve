package com.travelgo.backend.dto;

import com.travelgo.backend.domain.ItemDrop;
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

    private Long itemId;

    private LocalDateTime dropTime;

    public ItemDropDTO(ItemDrop itemDrop) {
        this.dropId = itemDrop.getDropId();
        this.userId = itemDrop.getUser().getUserId();
        this.itemId = itemDrop.getItem().getItemId();
        this.dropTime = itemDrop.getDropTime();
    }
}
