package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String employeeUrl;
    private String employeeIdUrl;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CompensationRepository compensationRepository;
    private Compensation compensation = new Compensation();
    private Employee employee = new Employee();
    private  String id;
    private String employeeId;
    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee/compensation";
        employeeIdUrl = "http://localhost:" + port + "/employee/compensation/{id}";
        id = "c0c2293d-16bd-4603-8e08-9153afb65309";
        employeeId = "c0c2293d-16bd-4603-78464638398-11111234";
        compensation.setEffectiveDate("10/04/2024");
        compensation.setSalary(4898.93);
        compensation.setEmployeeId(id);
        compensation.setDepartment("Business");
        compensation.setFirstName("Joe");
        compensation.setLastName("Newer");
        compensation.setPosition("Customer Service Representative");
        employee.setEmployeeId(employeeId);
        employee.setDepartment("Data");
        employee.setFirstName("Jessica");
        employee.setLastName("Tom");
        employee.setPosition("Data Analyst");
        employee.setDirectReports(new ArrayList<>());
        compensation.setDirectReports(new ArrayList<>(Arrays.asList(employee)));
        compensationRepository.insert(compensation);
    }

    @Test
    public void testCompensationCreation(){

        Compensation expectedCompensation = compensationRepository.findByEmployeeId(id);
        assertNotNull(expectedCompensation);
        assertEquals(compensation.getDirectReports().get(0), employee);
        assertTrue(compensation.getDirectReports().get(0).getFirstName() == employee.getFirstName());
        assertTrue(compensation.getDirectReports().get(0).getLastName() == employee.getLastName());


        //Test Compensation creation using the endpoints
        Compensation createdCompensation = restTemplate.postForEntity(employeeUrl, compensation, Compensation.class).getBody();
        assertNotNull(createdCompensation.getEmployeeId());
        compareCompensationProperties(compensation, createdCompensation);

        //Reading Compensation from DB
        Compensation readCompensation = restTemplate.getForEntity(employeeIdUrl, Compensation.class, createdCompensation.getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployeeId(), readCompensation.getEmployeeId());
        compareCompensationProperties(createdCompensation, readCompensation);
    }
    private void compareCompensationProperties(Compensation actual, Compensation expected){
        assertEquals(actual.getDirectReports().get(0).getPosition(), expected.getDirectReports().get(0).getPosition());
        assertEquals(actual.getEffectiveDate(), expected.getEffectiveDate());
        assertEquals(actual.getDepartment(), expected.getDepartment());
        assertTrue(actual.getSalary()== expected.getSalary());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertTrue(actual.getEmployeeId() != expected.getEmployeeId());
    }
}
