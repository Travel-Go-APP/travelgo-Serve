package com.travelgo.backend.repository;

import com.travelgo.backend.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
