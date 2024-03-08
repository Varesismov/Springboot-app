package com.example.demo.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<EmployeesGroup,Long> {

    @Query("SELECT g FROM EmployeesGroup g WHERE g.groupName = ?1")
    Optional<EmployeesGroup> findGroupsByGroupName(String groupName);
}
