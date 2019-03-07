package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {
    Department getById(Long id);

    List<Department> findAllByOrderByIdAsc();
}
