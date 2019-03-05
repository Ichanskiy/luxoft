package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ControllerAPI.EMPLOYEE_CONTROLLER)
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @CrossOrigin
    @GetMapping(value = ControllerAPI.ALL)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Get all employee");
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }
}
