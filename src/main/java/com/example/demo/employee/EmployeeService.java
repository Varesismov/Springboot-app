package com.example.demo.employee;

import com.example.demo.group.EmployeesGroup;
import com.example.demo.group.GroupRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, GroupRepository groupRepository) {
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
    }

    public List<Employee>getEmployees(){
        return employeeRepository.findAll();
    }

    public void addNewEmployee(Employee employee, Long groupId) {

//        Optional<Employee> employeeByFirstName =
//                employeeRepository.findEmployeeByFirstName(employee.getFirstName());
//        Optional<Employee>employeeByLastName =
//                employeeRepository.findEmployeeByLastName(employee.getLastName());
//
//        if(employeeByFirstName.isPresent() && employeeByLastName.isPresent()){
//            throw new IllegalStateException("Such employee already exists");
//        }

        EmployeesGroup selectedGroup = groupRepository.findById(groupId)
                .orElseThrow(
                        ()-> new IllegalStateException("Invalid group id")
                );

        if(selectedGroup.getEmployees().size() >= selectedGroup.getCapacity()){
            throw new IllegalStateException("Group limit has been reached");
        }

        employee.setEmployeesGroup(selectedGroup);
        selectedGroup.getEmployees().add(employee);

        employeeRepository.save(employee);
        groupRepository.save(selectedGroup);
    }

    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);

        if(!exists){
            throw new IllegalStateException("Group with ID: " + employeeId + " doesn't exist");
        }

        employeeRepository.deleteById(employeeId);
    }

    public ResponseEntity<byte[]> getEmployeesCSV() {
        List<Employee> allEmployees = employeeRepository.findAll();
        try(
                StringWriter stringWriter = new StringWriter();
                CSVWriter csvWriter = new CSVWriter(stringWriter);
        ){
            String[] columns = {"employeeID", "firstName", "lastName", "salary", "dateOfBirth", "age"};
            csvWriter.writeNext(columns);

            for(Employee employee: allEmployees){
                String[] employeeData = {
                        String.valueOf(employee.getId()),
                        employee.getFirstName(),
                        employee.getLastName(),
                        String.valueOf(employee.getSalary()),
                        String.valueOf(employee.getDateOfBirth()),
                        String.valueOf(employee.getAge())
                };

                csvWriter.writeNext(employeeData);
            }

            byte[] bytes = stringWriter.toString().getBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", "employees.csv");
            headers.setContentLength(bytes.length);

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
