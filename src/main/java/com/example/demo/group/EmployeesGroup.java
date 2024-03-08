package com.example.demo.group;

import com.example.demo.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class EmployeesGroup {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String groupName;

    private Integer capacity;

    @OneToMany(mappedBy = "employeesGroup", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Employee> employees;

    public EmployeesGroup() {
    }

    public EmployeesGroup(String groupName, Integer capacity) {
        this.groupName = groupName;
        this.capacity = capacity;
        this.employees = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
