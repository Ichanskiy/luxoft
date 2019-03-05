package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(ControllerAPI.DEPARTMENT_CONTROLLER)
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin
    @GetMapping(value = ControllerAPI.ALL)
    public ResponseEntity<List<Department>> getAllDepartments() {
        log.info("Get all departments");
        return new ResponseEntity<>(departmentService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity<Department> getDepartmentById(@PathVariable(name = "id") Long id) throws ParseException {
        log.info("Get department by id = " + id);
        Department department = departmentService.getDepartmentBiId(id);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        log.info("Create department");
        System.out.println(department.toString());
        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        log.info("Update department id = " + department.getId());
        if (departmentService.getDepartmentBiId(department.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(departmentService.updateDepartment(department), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity<Department> removeDepartmentById(@PathVariable(name = "id") Long id) {
        log.info("Remove department by id = " + id);
        departmentService.removeDepartmentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
