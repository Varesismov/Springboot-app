package com.example.demo.group;

import com.example.demo.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/group")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<EmployeesGroup> getGroups(){
        return groupService.getGroups();
    }

    @PostMapping
    public void addNewGroup(@RequestBody EmployeesGroup group){
        groupService.addNewGroup(group);
    }

    @DeleteMapping(path = "{employeesGroupId}")
    public void deleteGroup(@PathVariable("employeesGroupId")Long employeesGroupId){
        groupService.deleteGroup(employeesGroupId);
    }

    @GetMapping(path = "{employeesGroupId}/employee")
    public List<Employee> getEmployeesFromGroup(@PathVariable("employeesGroupId")Long employeesGroupId){
        return groupService.getEmployeesFromGroup(employeesGroupId);
    }

    @GetMapping(path = "{employeesGroupId}/fill")
    public String getGroupFill(@PathVariable("employeesGroupId")Long employeesGroupId){
        return groupService.getGroupFill(employeesGroupId);
    }
}
