package com.tw.apistackbase.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Company {

    private String companyName;
    private List<Employee> employeeList;

}
