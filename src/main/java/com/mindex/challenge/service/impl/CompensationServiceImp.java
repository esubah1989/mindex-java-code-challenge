package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompensationServiceImp implements CompensationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompensationServiceImp.class);
    @Autowired
    private CompensationRepository compensationRepository;
    @Override
    public Compensation create(Compensation compensation) {
        LOGGER.info("Creating employee name: {} {} effective date: {}", compensation.getFirstName(), compensation.getLastName(), compensation.getEffectiveDate());
        try {
            compensation.setEmployeeId(UUID.randomUUID().toString());
        }catch (Exception ex){
            LOGGER.error("Error creating employee: {} {} \n{}", compensation.getFirstName(), compensation.getLastName(), ex.getMessage());
        }
        return compensationRepository.insert(compensation);
    }

    @Override
    public Compensation read(String id) {
        Compensation compensation = compensationRepository.findByEmployeeId(id);
        if(compensation == null){
            throw new RuntimeException("Invalid employee id: " + id);
        }
        return compensation;
    }
}
