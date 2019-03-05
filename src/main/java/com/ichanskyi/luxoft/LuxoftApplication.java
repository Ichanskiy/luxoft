package com.ichanskyi.luxoft;

import com.ichanskyi.luxoft.services.DepartmentService;
import com.ichanskyi.luxoft.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LuxoftApplication implements CommandLineRunner {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(LuxoftApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Department department = new Department();
//        department.setAddress("testAddress");
//        department.addEmployee(new Employee()
//                .setDepartment(department)
//                .setPosition(Position.MIDDLE));
//        Department department1 = departmentService.saveDepartment(department);
//        departmentService.saveDepartment(department1);
//        departmentService.removeDepartmentById(department1.getId());
    }
}
