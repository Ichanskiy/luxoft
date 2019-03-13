package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getOne(id);
    }

    public List<Employee> getAllByDepartmentId(Long departmentId) {
        return employeeRepository.getAllByDepartmentIdOrderById(departmentId);
    }

    public void removeEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        if (isDuplicateEmail(employee)) {
            return null;
        }
        return employee.getId() == null ? createEmployee(employee) : updateEmployee(employee);
    }

    private Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    Employee updateEmployee(Employee employee) {
        Employee employeeDb = employeeRepository.getOne(employee.getId());
        if (isNotExist(employeeDb)) {
            return null;
        }
        employeeDb.setEmail(employee.getEmail());
        employeeDb.setName(employee.getName());
        employeeDb.setBirthday(employee.getBirthday());
        employeeDb.setMarried(employee.isMarried());
        employeeDb.setPosition(employee.getPosition());
        return employeeDb;
    }

    boolean isDuplicateEmail(Employee employee) {
        Employee employeeByEmail = employeeRepository.getEmployeeByEmail(employee.getEmail());
        return employeeByEmail != null && !employeeByEmail.getId().equals(employee.getId());
    }

    private boolean isNotExist(Employee employee) {
        return employee == null;
    }
}
