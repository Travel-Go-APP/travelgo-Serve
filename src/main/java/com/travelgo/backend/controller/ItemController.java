package com.travelgo.backend.controller;

import com.travelgo.backend.domain.Item;
import com.travelgo.backend.dto.ItemDTO;
import com.travelgo.backend.form.ItemForm;
import com.travelgo.backend.service.ItemService;
import com.travelgo.backend.service.PictureService;
import com.travelgo.backend.service.S3UploadService;
import com.travelgo.exception.GlobalErrorCode;
import com.travelgo.exception.TravelGoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
@Tag(name = "Item", description = "아이템 관련 API")
public class ItemController {
    private final ItemService itemService;
    private final S3UploadService s3UploadService;
    private final PictureService pictureService;

    @PostMapping(value = "")
    @Operation(summary = "아이템 저장")
    @ApiResponse(responseCode = "200", description = "아이템을 저장되고 s3에 이미지가 업로드 됩니다.")
    public ResponseEntity<?> saveItem(@Valid @RequestPart(value = "form", required = false) ItemForm form,
                                      @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        String fileUrl = s3UploadService.upload(image, "images");

        if (fileUrl == null)
            throw new TravelGoException(GlobalErrorCode.NULL_OBJECT);

        Item item = Item.builder()
                .grade(form.getGrade())
                .itemImage(fileUrl)
                .itemName(form.getItemName())
                .itemDescription(form.getItemDescription())
                .build();

        itemService.createItem(item);
        pictureService.saveItemPicture(image, item);

        ItemDTO itemDTO = new ItemDTO(item);

        return ResponseEntity.ok().body(itemDTO);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "아이템 삭제")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        Item findItem = itemService.findItemById(itemId);
        s3UploadService.fileDelete(findItem.getItemImage());
        itemService.deleteItem(findItem);

        return ResponseEntity.ok().body(itemId);
    }

    @GetMapping("")
    @Operation(summary = "Id로 아이템 찾기")
    public ResponseEntity<?> getLocationById(@RequestParam Long itemId) {
        Item findItem = itemService.findItemById(itemId);
        ItemDTO itemDTO = new ItemDTO(findItem);

        return ResponseEntity.ok().body(itemDTO);
    }

    @GetMapping("/findAllItem")
    @Operation(summary = "전체 아이템 찾기")
    public ResponseEntity<?> getItem() {
        List<Item> itemList = itemService.findAll();

        List<ItemDTO> itemDTOList = itemList.stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(itemDTOList);
    }

    @GetMapping("/finditemByname")
    @Operation(summary = "이름으로 아이템 찾기")
    public ResponseEntity<?> getItemByName(@RequestParam String itemName) {
        Item findItem = itemService.findItemByName(itemName);
        ItemDTO itemDTO = new ItemDTO(findItem);

        return ResponseEntity.ok().body(itemDTO);
    }
}
