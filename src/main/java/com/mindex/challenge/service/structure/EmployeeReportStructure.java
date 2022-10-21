package com.mindex.challenge.service.structure;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;

import java.util.List;

public class EmployeeReportStructure extends Employee {

    private int numberOfReports;

    public EmployeeReportStructure() {
        super();
    }
    public int countNumberOfReports(String id, EmployeeService employeeService){
        this.numberOfReports = 0;
        List<Employee> directReports = employeeService.read(id).getDirectReports();
        this.countNestedDirectReports(directReports, employeeService);
        return this.numberOfReports;
    }

    /***
     *
     * @param directReports list of employee directory
     * @param employeeService employee service use to look up directory
     */
    private void countNestedDirectReports(List<Employee> directReports, EmployeeService employeeService ){
        //If true, current employee does not have direct report
        if(directReports == null){
            return;
        }

        for (int i = 0; i < directReports.size(); i++) {
            Employee currentEmployee = employeeService.read(directReports.get(i).getEmployeeId());
            this.numberOfReports++;
            this.countNestedDirectReports(currentEmployee.getDirectReports(), employeeService);
        }
    }
    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
