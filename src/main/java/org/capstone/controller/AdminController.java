package org.capstone.controller;

import org.capstone.entity.Employee;
import org.capstone.exception.AdminException;
import org.capstone.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController (AdminService adminService) {
        this.adminService=adminService;
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployeesEndpoint() {
       List<Employee> employees = adminService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByIDEndpoint(@PathVariable int employeeId) {
        try {
            Employee employee = adminService.getEmployeeById(employeeId);
            return new ResponseEntity<>(employee,HttpStatus.OK);
        } catch (AdminException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
