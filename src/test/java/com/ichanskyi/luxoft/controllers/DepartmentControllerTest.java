package com.ichanskyi.luxoft.controllers;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.services.DepartmentService;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Objects;

import static com.ichanskyi.luxoft.controllers.ControllerAPI.*;
import static com.ichanskyi.luxoft.utils.JacksonUtils.getJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DepartmentControllerTest {

    private static final Long ID = 1L;
    private static final String ADDRESS = "address";
    private static final String NAME = "name";

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).dispatchOptions(true).build();
    }

    @Test
    @DisplayName("Get all Departments")
    void getAllDepartmentsTest() throws Exception {
        String request = DEPARTMENT_CONTROLLER + ALL;
        when(departmentService.getAll()).thenReturn(new ArrayList<>());
        MockHttpServletResponse response = mockMvc
                .perform(get(request))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assertNotEquals(response.getContentAsString(), Strings.EMPTY);
        verify(departmentService, times(1))
                .getAll();
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    @DisplayName("Get Department by id valid")
    void getDepartmentByIdTest() throws Exception {
        String request = DEPARTMENT_CONTROLLER + BY_ID;
        when(departmentService.getDepartmentById(ID)).thenReturn(getDepartment());
        MockHttpServletResponse response = mockMvc
                .perform(get(request, ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assertNotEquals(response.getContentAsString(), Strings.EMPTY);
        verify(departmentService, times(1))
                .getDepartmentById(ID);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    @DisplayName("Get Department by id invalid")
    void getDepartmentByIdTestInvalid() throws Exception {
        String request = DEPARTMENT_CONTROLLER + BY_ID;
        when(departmentService.getDepartmentById(ID)).thenReturn(null);
        MockHttpServletResponse response = mockMvc
                .perform(get(request, ID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertEquals(response.getContentAsString(), "Department is null");
        verify(departmentService, times(1))
                .getDepartmentById(ID);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    @DisplayName("Create Department")
    void createDepartmentTest() throws Exception {
        String request = DEPARTMENT_CONTROLLER + GENERAL_REQUEST;
        when(departmentService.createDepartment(getDepartment())).thenReturn(getDepartment());
        MockHttpServletResponse response = mockMvc
                .perform(post(request).content(Objects.requireNonNull(getJson(getDepartment())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assertNotEquals(response.getContentAsString(), Strings.EMPTY);
        verify(departmentService, times(1))
                .createDepartment(getDepartment());
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    @DisplayName("Remove department by id invalid")
    void removeDepartmentByIdTestInvalid() throws Exception {
        String request = DEPARTMENT_CONTROLLER + BY_ID;
        when(departmentService.getDepartmentById(ID)).thenReturn(null);
        mockMvc.perform(delete(request, ID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        verify(departmentService, times(1))
                .getDepartmentById(ID);
        verify(departmentService, times(0))
                .removeDepartmentById(ID);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    @DisplayName("Remove department by id")
    void removeDepartmentByIdTest() throws Exception {
        String request = DEPARTMENT_CONTROLLER + BY_ID;
        when(departmentService.getDepartmentById(ID)).thenReturn(getDepartment());
        mockMvc.perform(delete(request, ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(departmentService, times(1))
                .getDepartmentById(ID);
        verify(departmentService, times(1))
                .removeDepartmentById(ID);
        verifyNoMoreInteractions(departmentService);
    }

    private Department getDepartment() {
        return new Department()
                .setAddress(ADDRESS)
                .setName(NAME)
                .setEmployees(new ArrayList<>());
    }
}
