package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    List<Employee> getAllByDepartmentIdOrderById(Long departmentId);
}
