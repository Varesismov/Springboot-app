package com.example.demo.group;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GroupService {
    private final GroupRepository groupRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, EmployeeRepository employeeRepository) {
        this.groupRepository = groupRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeesGroup> getGroups() {
        return groupRepository.findAll();
    }

    public void addNewGroup(EmployeesGroup group) {
        Optional<EmployeesGroup> groupsByGroupName =
                groupRepository.findGroupsByGroupName(group.getGroupName());

        if(groupsByGroupName.isPresent()){
            throw new IllegalStateException("Such group already exists.");
        }

        groupRepository.save(group);
    }

    public void deleteGroup(Long employeesGroupId) {
        boolean exists = groupRepository.existsById(employeesGroupId);

        if(!exists){
            throw new IllegalStateException("Group with ID: " + employeesGroupId + " doesn't exist");
        }

        groupRepository.deleteById(employeesGroupId);
    }

    public List<Employee> getEmployeesFromGroup(Long employeesGroupId) {
        return employeeRepository.findEmployeeByGroupId(employeesGroupId);
    }

    public String getGroupFill(Long employeesGroupId) {
        EmployeesGroup selectedGroup = groupRepository.findById(employeesGroupId)
                .orElseThrow(
                        ()-> new IllegalStateException("Invalid group id")
                );

        Double fill = selectedGroup.getEmployees().size() * 100.0 / selectedGroup.getCapacity() ;

        return String.format("%.2f%%",fill);
    }

}
