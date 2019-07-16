package com.tw.apistackbase.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {

    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

}
