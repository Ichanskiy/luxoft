package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentBiId(Long id) {
        return departmentRepository.getById(id);
    }

    public Department saveEmployeeToDepartment(Long idDepartment, Employee employee) {
        Department department = departmentRepository.getOne(idDepartment);
        department.addEmployee(employee);
        return departmentRepository.save(department);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void removeDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

    public void removeDepartmentWithoutEmployeeById(Long id) {
        departmentRepository.removeDepartmentWithoutEmployeeById(id);
    }


    public Department updateDepartment(Department department) {
        return new Department();
    }
}
