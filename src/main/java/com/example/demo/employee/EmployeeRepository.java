package com.example.demo.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1")
    Optional<Employee> findEmployeeByFirstName(String firstName);

    @Query("SELECT e FROM Employee e WHERE e.lastName = ?1")
    Optional<Employee> findEmployeeByLastName(String lastName);

    @Query("SELECT e FROM Employee e where e.employeesGroup.id= ?1")
    List<Employee> findEmployeeByGroupId(Long employeesGroupId);
}
