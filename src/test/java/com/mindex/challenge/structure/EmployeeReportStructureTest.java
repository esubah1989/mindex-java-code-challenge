package com.mindex.challenge.structure;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.structure.EmployeeReportStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeReportStructureTest {
    @Autowired
    private EmployeeService employeeService;
    private String id1 = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    private String id2 = "03aa1462-ffa9-4978-901b-7c001562cf6f";
    private Employee employee;
    private EmployeeReportStructure employeeReportStructure;
    @Before
    public void setUp(){
        employee = employeeService.read(id1);
        employeeReportStructure = new EmployeeReportStructure();
        employeeReportStructure.setDirectReports(employee.getDirectReports());
        employeeReportStructure.setDepartment(employee.getDepartment());
        employeeReportStructure.setEmployeeId(employee.getEmployeeId());
        employeeReportStructure.setLastName(employee.getLastName());
        employeeReportStructure.setFirstName(employee.getFirstName());
        employeeReportStructure.setPosition(employee.getPosition());
    }
    @Test
    public void testReportStructure(){
        int numbOfReport1 = employeeReportStructure.countNumberOfReports(id1, employeeService);
        int numbOfReport2 = employeeReportStructure.countNumberOfReports(id2, employeeService);
        assertEquals(4, numbOfReport1);
        assertEquals(2,numbOfReport2);
    }
}
