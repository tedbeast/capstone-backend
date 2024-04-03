package org.capstone.service;
import org.capstone.entity.Employee;
import org.capstone.entity.WebexMessageType;
import org.capstone.exception.InvalidCredentialsException;
import org.capstone.exception.PasswordValidationException;
import org.capstone.exception.UserNotFoundException;
import org.capstone.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class LoginService {
    private final EmployeeRepository employeeRepository;
    private final WebexService webexService;

    @Autowired
    public LoginService(EmployeeRepository employeeRepository, WebexService webexService) {
        this.employeeRepository = employeeRepository;
        this.webexService = webexService;
    }

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
        // Check if the provided password matches the stored password
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            // User not found or incorrect password
            log.info(employeeID + " not found or incorrect password.");
            throw new InvalidCredentialsException("Authentication failed.Invalid credentials ");
        }
    }
    public void saveUser(Employee user) {
        if(user==null) {
            throw new IllegalArgumentException("User object is null");
        }            log.info(user + " has been saved to the database");
        employeeRepository.save(user);
    }
    public String updatePassword(int employeeID, String password) throws PasswordValidationException, UserNotFoundException {
        Employee user = employeeRepository.findByEmployeeID(employeeID);
        if (user == null) {
               throw new UserNotFoundException("User cannot be null.");
        }
        String userEmail = user.getEmail();
        if (user != null) {
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

           if (userEmail.contains("@geico.com")){
               try{
                   //Call Webex Service to send user notification that their password has been updated
                   webexService.sendMessage(userEmail, WebexMessageType.PASSWORD_RESET);
               }catch(Exception e){
                    log.warn(e.getMessage());
               }
           }
            return "Your Password has been updated successfully!";
        }
        return null;
    }
}