package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.dto.EmployeeDto;
import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ichanskyi.luxoft.exeptions.DuplicateEmailException.createDuplicateEmailException;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employeeDb = employeeRepository.findById(id);
        return employeeDb
                .map(employee ->
                        new EmployeeDto(employee.getId(),
                                employee.getEmail(),
                                employee.getName(),
                                employee.isMarried(),
                                employee.getBirthday(),
                                employee.getPosition(),
                                employee.getDepartment().getId()))
                .orElse(null);
    }

    public List<EmployeeDto> getAllByDepartmentId(Long departmentId) {
        return employeeRepository
                .getAllByDepartmentIdOrderById(departmentId)
                .stream()
                .map(employee ->
                        new EmployeeDto(employee.getId(),
                                employee.getEmail(),
                                employee.getName(),
                                employee.isMarried(),
                                employee.getBirthday(),
                                employee.getPosition(),
                                employee.getDepartment().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        if (isDuplicateEmail(employee)) {
            createDuplicateEmailException();
        }
        if (employeesIdIsNull(employee)) {
            createEmployee(employee);
        } else {
            updateEmployee(employee);
        }
    }

    private void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    private void updateEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        if (!employeeOptional.isPresent()) {
            createEntityNotFoundException();
        }
        Employee employeeDb = employeeOptional.get();
        employeeDb.setEmail(employee.getEmail());
        employeeDb.setName(employee.getName());
        employeeDb.setBirthday(employee.getBirthday());
        employeeDb.setMarried(employee.isMarried());
        employeeDb.setPosition(employee.getPosition());
    }

    private boolean isDuplicateEmail(Employee employee) {
        Employee employeeByEmail = employeeRepository.getEmployeeByEmail(employee.getEmail());
        return employeeByEmail != null && !employeeByEmail.getId().equals(employee.getId());
    }

    private boolean employeesIdIsNull(Employee employee) {
        return employee.getId() == null;
    }

    private void createEntityNotFoundException() {
        throw new EntityNotFoundException();
    }
}
