package com.tw.apistackbase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tw.apistackbase.Entity.Employee;
import com.tw.apistackbase.controller.EmployeeController;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/employees").param("page", "2").param("pageSize", "5")
        ).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<Employee> employees = JSONArray.parseArray(contentAsString, Employee.class);
        Assertions.assertEquals("John6", employees.get(0).getName());
    }


    @Test
    public void testGetOne() throws Exception {
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Employee employee = JSON.parseObject(contentAsString, Employee.class);
        Assertions.assertEquals("John1", employee.getName());
    }

    @Test
    public void testDelete() throws Exception {
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.delete("/employees/{id}", 1)
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("you delete John1 OK", contentAsString);
    }

    @Test
    public void testPut() throws Exception {

        String requestBody = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"John10000\",\n" +
                "        \"age\": 24,\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"salary\": 8001\n" +
                "    }";
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.put("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("update successful: John10000", contentAsString);
    }

    @Test
    public void testPost() throws Exception {
        String requestBody = "{\n" +
                "        \"id\": 10000,\n" +
                "        \"name\": \"John10000\",\n" +
                "        \"age\": 24,\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"salary\": 8001\n" +
                "    }";
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertNotNull(EmployeeController.result.get(10000));
    }
}
