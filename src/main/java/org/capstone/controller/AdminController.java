package org.capstone.controller;


import org.capstone.entity.Employee;

import org.capstone.entity.Manager;
import org.capstone.entity.PerformanceReview;
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


    @PostMapping("/manager")
    public ResponseEntity<Employee> createManager(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = adminService.createManager(employee);
            return ResponseEntity.ok(createdEmployee);
        } catch (AdminException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("employee/{employeeID}")
    public Employee updateEmployee(@PathVariable int employeeID, @RequestBody Employee employee) {
        try {
            return adminService.updateEmployee(employeeID, employee);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found", e);
        }
    }


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

    @GetMapping("/manager")
    public ResponseEntity<List<Manager>> getAllManagersEndpoint() {
        List<Manager> managers = adminService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @PostMapping("manager/{managerID}/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee p, @PathVariable int managerID) throws Exception {
        Employee employee = adminService.saveEmployee(managerID, p);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping("performance/average-rating-per-goal-type")
    public ResponseEntity<List<Object[]>> getAverageRatingPerGoalType() {
        return ResponseEntity.ok(adminService.getAverageRatingPerGoalType());
    }

//    @GetMapping("/performance/stats")
//    public ResponseEntity<PerformanceStatsDto> getPerformanceStats() {
//        PerformanceStatsDto stats = adminService.findPerformanceStats();
//        return new ResponseEntity<>(stats, HttpStatus.OK);
//    }

//    @GetMapping("/performance/stats")
//    public ResponseEntity<PerformanceStatsProjection> getPerformanceStats() {
//       PerformanceStatsProjection stats = adminService.findPerformanceStats();
//       return new ResponseEntity<>(stats, HttpStatus.OK);
//    }
}
