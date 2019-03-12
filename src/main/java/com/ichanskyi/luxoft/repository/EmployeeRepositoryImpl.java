package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee getEmployeeByEmail(String email) {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT em" +
                " FROM Employee as em" +
                " WHERE em.email =:email", Employee.class);
        query.setParameter("email", email);
        return query.getResultList()
                .stream().findFirst().orElse(null);
    }
}
