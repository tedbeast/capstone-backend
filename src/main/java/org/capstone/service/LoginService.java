package org.capstone.service;

import org.capstone.entity.Employee;
import org.capstone.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class LoginService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LoginService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee authenticate(int employeeID, String password) {
        Employee user = employeeRepository.findByEmployeeID(employeeID);
        System.out.println("Here is my login info: " + user);
        if (user != null && user.getPassword().equals(password)) {
            // Check if the provided password matches the stored password
            return user;
        }
        log.info(employeeID + " not found or incorrect password.");
        return null; // User not found or incorrect password
    }

    public void saveUser(Employee user) {
        log.info(user + " has been saved to the database");
        employeeRepository.save(user);
    }

    public String updatePassword(int employeeID, String password) {
        Employee user = employeeRepository.findByEmployeeID(employeeID);
        if (user != null) {
            if (user.getPassword().equals(password)) {

                   log.info("New password cannot be same as the current password!");
                return "New password cannot be same as the current password!";
            }
                if (password == null || password.isEmpty()){
                    log.info("Password cannot be blank!");
                    return "Password cannot be blank!";
                }
                user.setPassword(password);
                employeeRepository.save(user);
                   log.info("Your Password has been updated successfully!");
                return "Your Password has been updated successfully!";
            } else {
                   log.info("User does not exists!");
                return "No matching user exist!";
            }


        }
    }
