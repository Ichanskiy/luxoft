package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ControllerAPI.EMPLOYEE_CONTROLLER)
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity getEmployeeById(@PathVariable(name = "id") Long id) {
        log.info("Get employee by id = " + id);
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_DEPARTMENT_ID)
    public ResponseEntity getAllEmployeesByDepartmentId(@PathVariable(name = "id") Long id) {
        log.info("Get all employees by department`s id = " + id);
        return new ResponseEntity<>(employeeService.getAllByDepartmentId(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity saveEmployee(@RequestBody @Valid Employee employee) {
        log.info("Save employee");
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity removeEmployeeById(@PathVariable(name = "id") Long id) {
        log.info("Remove employee by id = " + id);
        employeeService.removeEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
