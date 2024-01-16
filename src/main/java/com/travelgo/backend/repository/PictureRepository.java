package com.travelgo.backend.repository;

import com.travelgo.backend.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture findByLocation_LocationId(Long locationId);
    Picture findByItem_ItemId(Long ItemId);
}
