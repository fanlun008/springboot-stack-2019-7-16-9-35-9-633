package com.tw.apistackbase.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Company {

    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employeeList = new ArrayList<>();

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public Company(String companyName, Integer employeesNumber) {
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
    }
}
