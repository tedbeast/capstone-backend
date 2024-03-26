package org.capstone.service;


import org.capstone.Main;
import org.capstone.entity.Employee;
import org.capstone.exception.AdminException;
import org.capstone.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    EmployeeRepository employeeRepository;

    @Autowired
    public AdminService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        Main.log.info("Employee List returned: " +employees);
        return employees;
    }

    public Employee getEmployeeById(int employeeId) throws AdminException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Main.log.info("Employee found: " + employee);
            return employeeOptional.get();
        } else {
            Main.log.warn("Employee not found" + employeeId);
            throw new AdminException("Employee is not found with that id" + employeeId);
        }
    }
}

