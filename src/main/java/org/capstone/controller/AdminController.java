package org.capstone.controller;

import org.capstone.entity.Employee;

import org.capstone.exception.AdminException;
import org.capstone.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
public class AdminController {
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = adminService.createEmployee(employee);
            return ResponseEntity.ok(createdEmployee);
        } catch (AdminException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Create Put Mapping

    @PutMapping("admin/{employeeID}")
    public Employee updateProduct(@PathVariable int employeeID, @RequestBody Employee employee) {
        try {
            return adminService.updateEmployee(employeeID, employee);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found", e);
        }
    }

    //Create Delete Mapping



    @DeleteMapping("employee/{employeeID}")

    public ResponseEntity<Employee>deleteById(@PathVariable int employeeID)throws Exception {
        try {
            adminService.deleteById(employeeID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (AdminException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
