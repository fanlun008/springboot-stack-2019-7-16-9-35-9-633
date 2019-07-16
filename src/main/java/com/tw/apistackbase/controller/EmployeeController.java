package com.tw.apistackbase.controller;

import com.tw.apistackbase.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    public static Map<Integer, Employee> result = new HashMap<>();

    static {
        for (int i = 1; i < 20; i++) {
            result.put(i, new Employee(i, "John"+i, 23+i, "Male", 8000+i));
        }
    }
    @GetMapping()
    public List<Employee> getAll(Integer page, Integer pageSize) {
        List<Employee> collect = result.values().stream().collect(Collectors.toList());
        if (page!= null) {
            List<Employee> result = new ArrayList<>();
            int startIndex = (page -1) * pageSize;
            int endIndex = (startIndex + (pageSize-1)) > collect.size()-1 ? collect.size()-1 : (startIndex + (pageSize-1));
            for (int i = startIndex; i <= endIndex; i++) {
                result.add(collect.get(i));
            }
            return result;
        }
        return collect;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id")Integer id) {
        Employee employee = result.get(id);
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody Employee employee) {
        result.put(employee.getId(), employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        Employee remove = result.remove(id);
        return ResponseEntity.ok().body(String.format("you delete %1$s OK", remove.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Employee employee){
        Employee employee1 = result.get(id);
        employee1.setAge(employee.getAge());
        employee1.setGender(employee.getGender());
        employee1.setName(employee.getName());
        employee1.setSalary(employee.getSalary());
        return ResponseEntity.ok().body("update successful: "+employee.getName());
    }

}
