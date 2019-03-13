package com.ichanskyi.luxoft.services;


import com.ichanskyi.luxoft.dto.DepartmentDto;
import com.ichanskyi.luxoft.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAll();

    DepartmentDto getDepartmentById(Long id);

    void saveDepartment(Department department);

    void removeDepartmentById(Long id);
}
