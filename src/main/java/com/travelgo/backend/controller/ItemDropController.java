package com.travelgo.backend.controller;

import com.travelgo.backend.domain.Item;
import com.travelgo.backend.domain.ItemDrop;
import com.travelgo.backend.domain.Location;
import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.ItemDropDTO;
import com.travelgo.backend.dto.Point;
import com.travelgo.backend.service.ItemDropService;
import com.travelgo.backend.service.ItemService;
import com.travelgo.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/itemdrop")
@Tag(name = "ItemDrop", description = "아이템 정보 관련 API")
public class ItemDropController {
    private final UserService userService;
    private final ItemService itemService;
    private final ItemDropService itemDropService;

    @PostMapping(value = "")
    @Operation(summary = "아이템 획득 저장")
    public ResponseEntity<?> saveItemDrop(@RequestParam Long userId, @RequestParam Long itemId) {
        User user = userService.findUserById(userId);
        Item item = itemService.findItemById(itemId);

        ItemDrop itemDrop = ItemDrop.builder()
                .user(user)
                .item(item)
                .dropTime(LocalDateTime.now())
                .build();

        itemDropService.createItemDrop(itemDrop);

        ItemDropDTO itemDropDTO = new ItemDropDTO(itemDrop);

        return ResponseEntity.ok().body(itemDropDTO);
    }

    @PatchMapping("/time/{dropId}")
    @Operation(summary = "획득 시간 변경")
    public ResponseEntity<?> updateItemDropTime(@PathVariable Long dropId) {
        ItemDrop itemDrop = itemDropService.findItemDropById(dropId);
        itemDropService.changeTimeNow(itemDrop);

        ItemDropDTO itemDropDTO = new ItemDropDTO(itemDrop);

        return ResponseEntity.ok().body(itemDropDTO);
    }

    @DeleteMapping("/{dropId}")
    @Operation(summary = "아이템 획득 정보 삭제")
    public ResponseEntity<?> deleteItemDrop(@PathVariable Long dropId) {
        ItemDrop itemDrop = itemDropService.findItemDropById(dropId);
        itemDropService.deleteItemDrop(itemDrop);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("")
    @Operation(summary = "Id로 아이템 획득 정보 찾기")
    public ResponseEntity<?> getItemDrop(@RequestParam Long dropId) {
        ItemDrop itemDrop = itemDropService.findItemDropById(dropId);
        ItemDropDTO itemDropDTO = new ItemDropDTO(itemDrop);

        return ResponseEntity.ok().body(itemDropDTO);
    }

    @GetMapping("/findall/itemdrop")
    @Operation(summary = "전체 아이템 획득 정보 찾기")
    public ResponseEntity<?> getItemDropAll() {
        List<ItemDrop> itemDropList = itemDropService.findAll();

        List<ItemDropDTO> dtoList = itemDropList.stream()
                .map(ItemDropDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/findall/itemdrop_user")
    @Operation(summary = "유저별 전체 아이템 획득 정보 찾기")
    public ResponseEntity<?> getItemDropAllByUser(@RequestParam Long userId) {
        List<ItemDrop> itemDropList = itemDropService.findAllByUser(userId);

        List<ItemDropDTO> dtoList = itemDropList.stream()
                .map(ItemDropDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dtoList);
    }

}
