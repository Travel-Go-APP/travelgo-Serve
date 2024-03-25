package com.travelgo.backend.repository;

import com.travelgo.backend.domain.ItemDrop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDropRepository extends JpaRepository<ItemDrop, Long> {

    List<ItemDrop> findAllByUser_UserId(Long userId);
}
