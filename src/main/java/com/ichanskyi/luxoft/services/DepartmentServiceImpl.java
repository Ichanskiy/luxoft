package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.dto.DepartmentDto;
import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDto> getAll() {
        List<Department> departments = departmentRepository.findAllByOrderByIdAsc();
        return departments
                .stream()
                .map(d ->
                        new DepartmentDto(d.getId(),
                                d.getName(),
                                d.getAddress()))
                .collect(toList());
    }

    @Transactional
    public DepartmentDto getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department
                .map(department1 -> new DepartmentDto(department1.getId(),
                        department1.getName(),
                        department1.getAddress()))
                .orElse(null);
    }

    @Transactional
    public void saveDepartment(Department department) {
        if (departmentsIdIsNull(department)) {
            createDepartment(department);
        } else {
            updateDepartment(department);
        }
    }

    private void createDepartment(Department department) {
        departmentRepository.save(department);
    }

    private void updateDepartment(Department department) {
        Department departmentDb = departmentRepository.getOne(department.getId());
        departmentDb.setAddress(department.getAddress());
        departmentDb.setName(department.getName());
    }

    @Transactional
    public void removeDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

    private boolean departmentsIdIsNull(Department department) {
        return department.getId() == null;
    }
}
