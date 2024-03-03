package com.travelgo.backend.service;

import com.travelgo.backend.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitService {
    private final VisitRepository visitRepository;
}
