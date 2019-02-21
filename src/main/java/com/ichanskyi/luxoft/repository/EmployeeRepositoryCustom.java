package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> getEmployeeByEmailLike(String email);
}
