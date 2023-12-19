package com.travelgo.backend.service;

import com.travelgo.backend.repository.ItemDropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDropService {

    private final ItemDropRepository itemDropRepository;

    @Autowired
    public ItemDropService(ItemDropRepository itemDropRepository){
        this.itemDropRepository = itemDropRepository;
    }
}
