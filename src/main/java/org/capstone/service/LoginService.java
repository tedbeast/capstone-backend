package org.capstone.service;

import org.capstone.entity.Employee;
import org.capstone.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class LoginService {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Employee authenticate(int employeeID, String password) {
        Employee user = loginRepository.findByEmployeeID(employeeID);
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
        loginRepository.save(user);
    }
}
