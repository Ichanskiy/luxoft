package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.entity.Employee;
import com.ichanskyi.luxoft.services.DepartmentService;
import com.ichanskyi.luxoft.services.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static com.ichanskyi.luxoft.controllers.ControllerAPI.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmployeeControllerTest {

    private static final Long ID = 1L;

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).dispatchOptions(true).build();
    }

    @Test
    @DisplayName("Get Employee by id valid")
    void getEmployeeByIdTest() throws Exception {
        String request = EMPLOYEE_CONTROLLER + BY_ID;
        when(employeeService.getEmployeeById(ID)).thenReturn(new Employee());
        MockHttpServletResponse response = mockMvc
                .perform(get(request, ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assertNotEquals(response.getContentAsString(), Strings.EMPTY);
        verify(employeeService, times(1))
                .getEmployeeById(ID);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    @DisplayName("Get Employee by id invalid")
    void getEmployeeByIdTestInvalid() throws Exception {
        String request = EMPLOYEE_CONTROLLER + BY_ID;
        when(employeeService.getEmployeeById(ID)).thenReturn(null);
        MockHttpServletResponse response = mockMvc
                .perform(get(request, ID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertEquals(response.getContentAsString(), "Employee is null");
        verify(employeeService, times(1))
                .getEmployeeById(ID);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    @DisplayName("Get get all employees by department`s id")
    void getAllEmployeesByDepartmentIdTest() throws Exception {
        String request = EMPLOYEE_CONTROLLER + BY_DEPARTMENT_ID;
        when(departmentService.getDepartmentById(ID)).thenReturn(new Department());
        when(employeeService.getAllByDepartmentId(ID)).thenReturn(new ArrayList<>());
        MockHttpServletResponse response = mockMvc
                .perform(get(request, ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assertNotEquals(response.getContentAsString(), Strings.EMPTY);
        verify(departmentService, times(1))
                .getDepartmentById(ID);
        verify(employeeService, times(1))
                .getAllByDepartmentId(ID);
        verifyNoMoreInteractions(departmentService);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    @DisplayName("Get get all employees by invalid department`s id")
    void getAllEmployeesByDepartmentInvalidIdTest() throws Exception {
        String request = EMPLOYEE_CONTROLLER + BY_DEPARTMENT_ID;
        when(departmentService.getDepartmentById(ID)).thenReturn(null);
        MockHttpServletResponse response = mockMvc
                .perform(get(request, ID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertNotEquals(response.getContentAsString(), Strings.EMPTY);
        verify(departmentService, times(1))
                .getDepartmentById(ID);
        verify(employeeService, times(0))
                .getAllByDepartmentId(ID);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    @DisplayName("Remove Employee by id")
    void removeEmployeeByIdTest() throws Exception {
        String request = EMPLOYEE_CONTROLLER + BY_ID;
        when(employeeService.getEmployeeById(ID)).thenReturn(new Employee());
        mockMvc.perform(delete(request, ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(employeeService, times(1))
                .getEmployeeById(ID);
        verify(employeeService, times(1))
                .removeEmployeeById(ID);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    @DisplayName("Remove Employee by id invalid")
    void removeEmployeeByIdTestInvalid() throws Exception {
        String request = EMPLOYEE_CONTROLLER + BY_ID;
        when(employeeService.getEmployeeById(ID)).thenReturn(null);
        mockMvc.perform(delete(request, ID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        verify(employeeService, times(1))
                .getEmployeeById(ID);
        verify(employeeService, times(0))
                .removeEmployeeById(ID);
        verifyNoMoreInteractions(employeeService);
    }
}
