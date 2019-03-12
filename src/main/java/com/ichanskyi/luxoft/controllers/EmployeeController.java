package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Department;
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

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

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
    public ResponseEntity createEmployee(@RequestBody @Valid Employee employee) {
        log.info("Create employee");
        Employee employeeDb = employeeService.getEmployeeByEmail(employee.getEmail());
        if (employeeDb != null) {
            return new ResponseEntity<>("Duplicate email", HttpStatus.BAD_REQUEST);
        }
        Department department = departmentService.getDepartmentById(employee.getDepartment().getId());
        if (department == null) {
            return new ResponseEntity<>("Department is null", HttpStatus.BAD_REQUEST);
        }
        employeeService.createEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity updateEmployee(@RequestBody @Valid Employee employee) {
        log.info("Update employee");
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        if (updatedEmployee == null) {
            return new ResponseEntity<>("Duplicate email", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
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
