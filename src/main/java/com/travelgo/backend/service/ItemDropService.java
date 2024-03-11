package com.travelgo.backend.service;

import com.travelgo.backend.domain.Item;
import com.travelgo.backend.domain.User;
import com.travelgo.backend.repository.ItemDropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemDropService {

    private final ItemDropRepository itemDropRepository;

    public void createItemDrop(User user, Item item){

    }
}
