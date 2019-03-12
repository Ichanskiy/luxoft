package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Employee;

public interface EmployeeRepositoryCustom {
    Employee getEmployeeByEmail(String email);
}
