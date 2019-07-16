package com.tw.apistackbase;


import com.alibaba.fastjson.JSON;
import com.tw.apistackbase.Entity.Company;
import com.tw.apistackbase.controller.CompanyController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTest{

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetOne() throws Exception {
        String conpany1 = mockMvc.perform(
                get("/companies/{name}", "alibaba")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Company company = JSON.parseObject(conpany1, Company.class);
        Assertions.assertSame(20, company.getEmployeesNumber());
    }

    @Test
    public void testPost() throws Exception {
        String requestBody = "{\n" +
                "    \"companyName\": \"company233456\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employeeList\": []\n" +
                "}";
        String contentAsString = mockMvc.perform(
                post("/companies").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("add successful", contentAsString);
        Assertions.assertNotNull(CompanyController.companyMap.get("company233456"));
    }

    @Test
    public void testDelete() throws Exception {
        String alibaba = mockMvc.perform(
                delete("/companies/{name}", "alibaba")
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertNull(CompanyController.companyMap.get("alibaba"));
    }
}
