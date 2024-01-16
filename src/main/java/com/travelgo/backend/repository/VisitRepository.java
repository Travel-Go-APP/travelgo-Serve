package com.travelgo.backend.repository;

import com.travelgo.backend.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
