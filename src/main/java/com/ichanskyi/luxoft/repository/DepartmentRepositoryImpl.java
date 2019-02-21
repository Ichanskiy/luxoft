package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Department;
import com.ichanskyi.luxoft.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Iterator;

@Repository
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class DepartmentRepositoryImpl implements DepartmentRepositoryCustom {

    @Autowired
    private EntityManagerFactory emf;

    @Override
    @Deprecated
    public void removeDepartmentWithoutEmployeeById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Department department = entityManager.find(Department.class, id);
        if (department.getEmployees() != null) {
            Iterator<Employee> iterator = department.getEmployees().iterator();
            while (iterator.hasNext()) {
                Employee nextEmployee = iterator.next();
                iterator.remove();
                department.removeEmployee(nextEmployee);
            }
        }
        entityManager.remove(department);
        tx.commit();
        entityManager.close();
    }
}
