package org.capstone.controller;

import org.capstone.entity.Employee;

import org.capstone.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class AdminController {
    AdminService adminService;

    public AdminController (AdminService adminService){
        this.adminService =adminService;
    }

    // Create Put Mapping

    @PutMapping("admin/{employeeID}")
    public Employee updateProduct(@PathVariable int employeeID, @RequestBody Employee employee) {
        try {
            return adminService.updateSiteUser(employeeID, employee);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found", e);
        }
    }

    // Create Delete Mapping
}
