package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {
    Department getById(Long id);
}
