package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.services.DepartmentService;
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
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity getEmployeeById(@PathVariable(name = "id") Long id) {
        log.info("Get employee by id = " + id);
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>("Employee is null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_DEPARTMENT_ID)
    public ResponseEntity getAllEmployeesByDepartmentId(@PathVariable(name = "id") Long id) {
        log.info("Get all employees by department`s id = " + id);
        if (departmentService.getDepartmentById(id) == null) {
            return new ResponseEntity<>("Department is null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeService.getAllByDepartmentId(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity saveEmployee(@RequestBody @Valid Employee employee) {
        log.info("Create employee");
        if (departmentService.isNotExist(employee.getDepartment().getId())) {
            return new ResponseEntity<>("Department is null", HttpStatus.BAD_REQUEST);
        }
        Employee employeeDb = employeeService.saveEmployee(employee);
        if (employeeDb == null) {
            return new ResponseEntity<>("Duplicate email", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity removeEmployeeById(@PathVariable(name = "id") Long id) {
        log.info("Remove employee by id = " + id);
        if (employeeService.getEmployeeById(id) == null) {
            return new ResponseEntity<>("Employee is null", HttpStatus.BAD_REQUEST);
        }
        employeeService.removeEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
