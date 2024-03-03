package com.travelgo.backend.service;

import com.travelgo.backend.domain.Item;
import com.travelgo.backend.domain.Picture;
import com.travelgo.backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void createItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    @Transactional
    public void updateItemPicture(Item item, Picture image) {
        item.changeItemImage(image.getImageUrl());
    }

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(itemId + "가 존재하지 않습니다."));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findItemByName(String itemName) {
        return itemRepository.findByItemName(itemName);
    }
}
