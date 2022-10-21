package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DataBootstrap {
    private static final String DATASTORE_LOCATION = "/static/employee_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        Employee[] employees = null;

        try {
            employees = objectMapper.readValue(inputStream, Employee[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initDatabaseWithCompensation();
        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }
    }
    private void initDatabaseWithCompensation(){
        InputStream inputStream = this.getClass().getResourceAsStream("/static/employeeCompensation_database.json");
        Compensation compensation = null;
       try{
           compensation = objectMapper.readValue(inputStream, Compensation.class);
        if(compensation != null) {
            compensationRepository.insert(compensation);
        }
        }catch (Exception ex ){
           throw new RuntimeException(ex);
       }
    }
}
