package com.travelgo.backend.repository;

import com.travelgo.backend.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
