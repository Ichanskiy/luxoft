package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.getByEmail(email);
    }

    public void removeEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllByDepartmentId(Long departmentId) {
        return employeeRepository.getAllByDepartmentIdOrderById(departmentId);
    }

    @Transactional
    public void createEmployee(Employee employee) {
        Department department = departmentRepository.getById(employee.getDepartment().getId());
        department.addEmployee(employee);
    }

    public List<Employee> getEmployeeByEmailLike(String email) {
        return employeeRepository.getEmployeeByEmailLike(email);
    }

    @Transactional
    public Employee updateEmployee(Employee employee) {
        Employee employeeDb = employeeRepository.getById(employee.getId());
        if (isNotExist(employeeDb) || isDuplicateEmail(employee)) {
            return null;
        }
        employeeDb.setEmail(employee.getEmail());
        employeeDb.setName(employee.getName());
        employeeDb.setBirthday(employee.getBirthday());
        employeeDb.setMarried(employee.isMarried());
        employeeDb.setPosition(employee.getPosition());
        return employeeDb;
    }

    private boolean isDuplicateEmail(Employee employee) {
        Employee employeeByEmail = employeeRepository.getByEmail(employee.getEmail());
        return employeeByEmail != null && !employeeByEmail.getId().equals(employee.getId());
    }

    private boolean isNotExist(Employee employee) {
        return employee == null;
    }
}
