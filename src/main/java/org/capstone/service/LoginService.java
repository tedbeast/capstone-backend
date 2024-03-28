package org.capstone.service;

import org.capstone.entity.Employee;
import org.capstone.exception.InvalidCredentialsException;
import org.capstone.exception.PasswordValidationException;
import org.capstone.exception.UserNotFoundException;
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


    //swathi-Added InvalidCredentials exception here
    public Employee authenticate(int employeeID, String password) {
        Employee user = employeeRepository.findByEmployeeID(employeeID);
        System.out.println("Here is my login info: " + user);
        if (employeeID < 1) {
            throw new InvalidCredentialsException("EmployeeID must be a positive integer greater than or equal to 1.");
        }

        // Check if employeeID contains only digits
        if (!String.valueOf(employeeID).matches("\\d+")) {
            throw new InvalidCredentialsException("EmployeeID must contain only digits.");
        }

        if (user != null && user.getPassword().equals(password)) {
            // Check if the provided password matches the stored password
            return user;
        } else {

            log.info(employeeID + " not found or incorrect password.");
            throw new InvalidCredentialsException("Authentication failed.Invalid credentials ");
            // User not found or incorrect password
        }
    }
        //swathi-Added Illegal argument exception
    public void saveUser(Employee user) {
        if(user==null) {
            throw new IllegalArgumentException("User object is null");
        }            log.info(user + " has been saved to the database");
        employeeRepository.save(user);
    }
  //swathi-Added UserNotFound and passwordValidationException
    public String updatePassword(int employeeID, String password) throws PasswordValidationException, UserNotFoundException {
        Employee user = employeeRepository.findByEmployeeID(employeeID);
        if (user == null) {
               throw new UserNotFoundException("User cannot be null.");
        }
        if (user.getPassword().equals(password)) {
            log.info("New password cannot be same as the current password!");
            throw new PasswordValidationException("New password cannot be same as the the current password");

        }
        if (password == null || password.isEmpty()) {
            log.info("Password cannot be blank!");
            throw new PasswordValidationException("Password cannot be blank");

        }
        user.setPassword(password);
        employeeRepository.save(user);
        log.info("Your Password has been updated successfully!");
        return "Your Password has been updated successfully!";
    }

    }


