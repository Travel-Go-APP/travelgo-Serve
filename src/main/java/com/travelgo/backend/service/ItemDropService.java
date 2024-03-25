package com.travelgo.backend.service;

import com.travelgo.backend.domain.ItemDrop;
import com.travelgo.backend.repository.ItemDropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemDropService {

    private final ItemDropRepository itemDropRepository;

    @Transactional
    public void createItemDrop(ItemDrop itemDrop) {
        itemDropRepository.save(itemDrop);
    }

    @Transactional
    public void deleteItemDrop(ItemDrop itemDrop) {
        itemDropRepository.delete(itemDrop);
    }

    @Transactional
    public void changeTimeNow(ItemDrop itemDrop) {
        itemDrop.changeDropTime();
    }

    public ItemDrop findItemDropById(Long dropId) {
        return itemDropRepository.findById(dropId).orElseThrow(() -> new NotFoundException(dropId + "가 존재하지 않습니다."));
    }

    public List<ItemDrop> findAll() {
        return itemDropRepository.findAll();
    }

    public List<ItemDrop> findAllByUser(Long userId) {
        return itemDropRepository.findAllByUser_UserId(userId);
    }
}
