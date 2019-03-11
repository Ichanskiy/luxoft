package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test.application.properties")
class DepartmentServiceTest {

    private static final String ADDRESS_OLD = "address_old";
    private static final String ADDRESS_NEW = "address_new";
    private static final String NAME_OLD = "name_old";
    private static final String NAME_NEW = "name_new";

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    private Department departmentGlobal;

    @BeforeEach
    void setUp() {
        departmentGlobal = departmentRepository
                .save(new Department()
                        .setAddress(ADDRESS_OLD)
                        .setName(NAME_OLD));
    }

    @AfterEach
    void tearDown() {
        departmentRepository.deleteById(departmentGlobal.getId());
    }


    @Test
    @DisplayName("Update Department")
    void updateDepartmentTest() {
        Department department = departmentRepository.getById(departmentGlobal.getId());
        department.setName(NAME_NEW);
        department.setAddress(ADDRESS_NEW);
        departmentService.updateDepartment(department);

        Department departmentAfterUpdate = departmentRepository.getById(department.getId());
        assertEquals(departmentAfterUpdate.getId(), department.getId());
        assertEquals(departmentAfterUpdate.getName(), NAME_NEW);
        assertEquals(departmentAfterUpdate.getAddress(), ADDRESS_NEW);
    }
}
