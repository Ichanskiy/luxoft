package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getOne(id);
    }

    public void removeEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllByDepartmentId(Long departmentId) {
        return employeeRepository.getAllByDepartmentId(departmentId);
    }

    public void createEmployee(Employee employee) {
        Department department = departmentRepository.getById(employee.getDepartment().getId());
        department.addEmployee(employee);
        departmentRepository.save(department);
    }

    public List<Employee> getEmployeeByEmailLike(String email) {
        return employeeRepository.getEmployeeByEmailLike(email);
    }
}
