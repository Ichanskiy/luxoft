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
import java.util.Optional;

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
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.
                <ResponseEntity>map(employee1 ->
                        new ResponseEntity<>(employee1, HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>("Employee is null", HttpStatus.BAD_REQUEST));
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_DEPARTMENT_ID)
    public ResponseEntity getAllEmployeesByDepartmentId(@PathVariable(name = "id") Long id) {
        log.info("Get all employees by department`s id = " + id);
        if (departmentService.isNotExist(id)) {
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
        if (!employeeService.saveEmployee(employee).isPresent()) {
            return new ResponseEntity<>("Duplicate email", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity removeEmployeeById(@PathVariable(name = "id") Long id) {
        log.info("Remove employee by id = " + id);
        if (employeeService.isNotExist(id)) {
            return new ResponseEntity<>("Employee is null", HttpStatus.BAD_REQUEST);
        }
        employeeService.removeEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
