package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ControllerAPI.DEPARTMENT_CONTROLLER)
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.ALL)
    public ResponseEntity<List<Department>> getAllDepartments() {
        log.info("Get all departments");
        return new ResponseEntity<>(departmentService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity getDepartmentById(@PathVariable(name = "id") Long id) {
        log.info("Get department by id = " + id);
        Optional<Department> department = departmentService.getDepartmentById(id);
        if (!department.isPresent()) {
            return new ResponseEntity<>("Department is null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity saveDepartment(@RequestBody @Valid Department department) {
        return new ResponseEntity<>(departmentService.saveDepartment(department), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity removeDepartmentById(@PathVariable(name = "id") Long id) {
        log.info("Remove department by id = " + id);
        if (departmentService.isNotExist(id)) {
            return new ResponseEntity<>("Department is null", HttpStatus.BAD_REQUEST);
        }
        departmentService.removeDepartmentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
