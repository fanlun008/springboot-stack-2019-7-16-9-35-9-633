package com.tw.apistackbase.controller;

import com.tw.apistackbase.Entity.Company;
import com.tw.apistackbase.Entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private static Map<String, Company> companyMap = new HashMap<>();

    static {

        Company companyA = new Company("alibaba", 20);
        Company companyB = new Company("tencent", 30);
        for (int i = 1; i< 21; i++) {
            Employee employee = new Employee(i, "John"+i, 12+i, "Male", 8000+i);
            companyA.getEmployeeList().add(employee);
        }

        for (int i = 1; i< 31; i++) {
            Employee employee = new Employee(i, "Bill"+i, 13+i, "Male", 6000+i);
            companyB.getEmployeeList().add(employee);
        }

        companyMap.put(companyA.getCompanyName(), companyA);
        companyMap.put(companyB.getCompanyName(), companyB);
        for (int i = 1; i< 7; i++) {
            companyMap.put("company"+i, new Company("company"+i, i));
        }
    }

    @GetMapping("/{name}")
    public Company getOneCompany(@PathVariable("name") String name) {
        Company company = companyMap.get(name);
        return company;
    }

    @GetMapping("/{name}/employees")
    public List<Employee> getAllEmployeeUnderCompany(@PathVariable("name") String name) {
        return companyMap.get(name).getEmployeeList();
    }

    @GetMapping()
    public List<Company> getSomeByPagination(Integer page, Integer pageSize) {
        List<Company> collect = companyMap.values().stream().collect(Collectors.toList());
        if (page != null) {
            List<Company> result = new ArrayList<>();
            int startIndex = (page - 1) * pageSize;
            int endIndex = (startIndex + (pageSize-1)) > collect.size()-1 ? collect.size()-1 : (startIndex + (pageSize-1));
            for (int i = startIndex; i <= endIndex; i++) {
                result.add(collect.get(i));
            }
            return result;
        }
        return collect;
    }

    @PostMapping()
    public ResponseEntity addCompany(@RequestBody Company company) {
        Company put = companyMap.put(company.getCompanyName(), company);
        return ResponseEntity.ok().body("add successful");
    }

}
