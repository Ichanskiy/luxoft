package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAll() {
        return departmentRepository.findAllByOrderByIdAsc();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.getOne(id);
    }

    public boolean isNotExist(Long id) {
        return !departmentRepository.existsById(id);
    }

    @Transactional
    public Department saveDepartment(Department department) {
        return department.getId() == null ? createDepartment(department) : updateDepartment(department);
    }

    private Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    private Department updateDepartment(Department department) {
        Department departmentDb = departmentRepository.getOne(department.getId());
        departmentDb.setAddress(department.getAddress());
        departmentDb.setName(department.getName());
        return departmentDb;
    }

    public void removeDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

}
