package com.ichanskyi.luxoft.repository;

import com.ichanskyi.luxoft.entity.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
//@Transactional(readOnly = true)
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getEmployeeByEmailLike(String email) {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT em" +
                " FROM Employee as em" +
                " WHERE em.email LIKE CONCAT('%',:email,'%')", Employee.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
