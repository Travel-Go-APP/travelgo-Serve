package com.travelgo.backend.repository;

import com.travelgo.backend.domain.Area;
import com.travelgo.backend.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationName(String name);
    List<Location> findAllByArea(Area area);
}
