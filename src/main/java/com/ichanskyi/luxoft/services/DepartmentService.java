package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAllByOrderByIdAsc();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.getOne(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void removeDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Transactional
    public Department updateDepartment(Department department) {
        Department departmentDb = departmentRepository.getOne(department.getId());
        departmentDb.setAddress(department.getAddress());
        departmentDb.setName(department.getName());
        return departmentDb;
    }
}
