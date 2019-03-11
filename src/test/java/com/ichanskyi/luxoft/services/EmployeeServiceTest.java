package com.ichanskyi.luxoft.services;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.entity.enums.Position;
import com.ichanskyi.luxoft.repository.DepartmentRepository;
import com.ichanskyi.luxoft.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test.application.properties")
class EmployeeServiceTest {

    private static final String EMAIL_OLD = "email_old";
    private static final String EMAIL_NEW_FIRST = "email_new_first";
    private static final String EMAIL_NEW_SECOND = "email_new_second";
    private static final String NAME_OLD = "name_old";
    private static final String NAME_NEW = "name_new";
    private static final Position POSITION_OLD = Position.INTERN;
    private static final Position POSITION_NEW = Position.JUNIOR;
    private static final boolean IS_MARRIED = true;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeService employeeService;

    private Employee employeeGlobal;
    private Department departmentGlobal;

    @BeforeEach
    void setUp() {
        employeeGlobal = employeeRepository
                .save(new Employee()
                        .setBirthday(new Date())
                        .setEmail(EMAIL_OLD)
                        .setName(NAME_OLD)
                        .setMarried(IS_MARRIED)
                        .setPosition(POSITION_OLD));
        departmentGlobal = departmentRepository
                .save(new Department()
                        .setName(NAME_OLD));
        departmentGlobal.addEmployee(employeeGlobal);
        departmentRepository.save(departmentGlobal);
    }

    @AfterEach
    void tearDown() {
        departmentRepository.deleteById(departmentGlobal.getId());
    }

    @Test
    @DisplayName("Update employee valid")
    void updateEmployeeTest() {
        Employee employee = employeeRepository.getById(employeeGlobal.getId());
        employee.setName(NAME_NEW);
        employee.setMarried(!IS_MARRIED);
        employee.setEmail(EMAIL_NEW_FIRST);
        employee.setPosition(POSITION_NEW);
        employeeService.updateEmployee(employee);

        Employee employeeAfterUpdate = employeeRepository.getById(employee.getId());
        assertEquals(employeeAfterUpdate.getId(), employeeAfterUpdate.getId());
        assertNotNull(employeeAfterUpdate.getBirthday());
        assertEquals(employeeAfterUpdate.getName(), NAME_NEW);
        assertEquals(employeeAfterUpdate.isMarried(), !IS_MARRIED);
        assertEquals(employeeAfterUpdate.getEmail(), EMAIL_NEW_FIRST);
        assertEquals(employeeAfterUpdate.getPosition(), POSITION_NEW);
    }


    @DisplayName("is Duplicate Email")
    @ParameterizedTest(name = "run #{index} with [{arguments}]")
    @CsvSource({EMAIL_NEW_FIRST, EMAIL_NEW_SECOND})
    void isDuplicateEmailTest(String emailNew) {
        Employee employeeDb = employeeRepository
                .save(new Employee()
                        .setEmail(emailNew));
        employeeDb.setDepartment(departmentRepository.getById(departmentGlobal.getId()));
        employeeRepository.save(employeeDb);
        assertFalse(employeeService.isDuplicateEmail(employeeDb));
    }
}
