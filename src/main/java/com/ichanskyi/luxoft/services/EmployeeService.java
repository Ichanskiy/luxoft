package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean isNotExist(Long id) {
        return !employeeRepository.existsById(id);
    }

    @Transactional
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllByDepartmentId(Long departmentId) {
        return employeeRepository.getAllByDepartmentIdOrderById(departmentId);
    }

    public void removeEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public Optional<Employee> saveEmployee(Employee employee) {
        if (isDuplicateEmail(employee)) {
            return Optional.empty();
        }
        return employee.getId() == null ? createEmployee(employee) : updateEmployee(employee);
    }

    private Optional<Employee> createEmployee(Employee employee) {
        return Optional.of(employeeRepository.save(employee));
    }

    Optional<Employee> updateEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        if (!employeeOptional.isPresent()) {
            return Optional.empty();
        }
        Employee employeeDb = employeeOptional.get();
        employeeDb.setEmail(employee.getEmail());
        employeeDb.setName(employee.getName());
        employeeDb.setBirthday(employee.getBirthday());
        employeeDb.setMarried(employee.isMarried());
        employeeDb.setPosition(employee.getPosition());
        return Optional.of(employeeDb);
    }

    boolean isDuplicateEmail(Employee employee) {
        Employee employeeByEmail = employeeRepository.getEmployeeByEmail(employee.getEmail());
        return employeeByEmail != null && !employeeByEmail.getId().equals(employee.getId());
    }
}
