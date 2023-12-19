package com.travelgo.backend.service;

import com.travelgo.backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository){
        this.visitRepository = visitRepository;
    }
}
