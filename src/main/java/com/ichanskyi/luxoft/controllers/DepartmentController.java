package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.dto.DepartmentDto;
import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        log.info("Get all departments");
        return new ResponseEntity<>(departmentService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity getDepartmentById(@PathVariable(name = "id") Long id) {
        log.info("Get department by id = " + id);
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = ControllerAPI.GENERAL_REQUEST)
    public ResponseEntity saveDepartment(@RequestBody @Valid Department department) {
        departmentService.saveDepartment(department);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = ControllerAPI.BY_ID)
    public ResponseEntity removeDepartmentById(@PathVariable(name = "id") Long id) {
        log.info("Remove department by id = " + id);
        departmentService.removeDepartmentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
