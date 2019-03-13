package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.dto.EmployeeDto;
import com.ichanskyi.luxoft.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getAllByDepartmentId(Long departmentId);

    void saveEmployee(Employee employee);

    void removeEmployeeById(Long id);
}
