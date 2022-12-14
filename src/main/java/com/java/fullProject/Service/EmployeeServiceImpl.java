package com.java.fullProject.Service;


import com.java.fullProject.CustomExceptions.ResourceNotFound;
import com.java.fullProject.Entity.Employees;
import com.java.fullProject.Repository.EmployeeRepo;
import com.java.fullProject.Service.EmployeeService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    EmployeeRepo employeeRepo;
    @Value("${topic.name}")
    private String topicName;

    @Override
    public Employees saveEmployees(Employees employees) {
        return employeeRepo.save(employees);
    }

    @Override
    public List<Employees> getAllEmployees() {
        List<Employees> employeesList = employeeRepo.findAll();
        return employeesList;
    }

    public Employees getEmployeeById(int id) {
//        Optional<Employees> employees= employeeRepo.findById(id);
//        if(employees.isPresent()){
//            return employees.get();
//        }
//        else {
//            throw new ResourceNotFound("Employee", "id", id);
//        }
        //same in lambda
        return employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Employee", "id", id));
    }

    @Override
    public Employees updateEmployee(Employees updatedEmployeeData, int id) {

        //We need to check if the id is present or not
        Employees existingEmployee = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Employee", "id", id));

        // Now we will set our data from the newly fetched data.
        existingEmployee.setMessage_id(updatedEmployeeData.getMessage_id());
        existingEmployee.setStatus(updatedEmployeeData.getStatus());
        existingEmployee.setVersion_id(updatedEmployeeData.getVersion_id());

        //Now we will save the updated data
        employeeRepo.save(existingEmployee);
        return existingEmployee;
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Employee", "id", id));
        employeeRepo.deleteById(id);
    }
}
