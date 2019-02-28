package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getById(Long id) {
        return employeeRepository.getOne(id);
    }

    public void removeEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeeByEmailLike(String email) {
        return employeeRepository.getEmployeeByEmailLike(email);
    }
}
