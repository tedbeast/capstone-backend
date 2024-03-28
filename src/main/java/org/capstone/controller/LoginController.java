package org.capstone.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.capstone.entity.Employee;
import org.capstone.exception.InvalidCredentialsException;
import org.capstone.exception.PasswordValidationException;
import org.capstone.exception.UserNotFoundException;
import org.capstone.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
//@CrossOrigin(origins="http://localhost:3000/login")
public class LoginController {

  public final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @GetMapping("/")
  public ResponseEntity<String> home() {
    String welcome = "Welcome to my Login page!";
    return ResponseEntity.ok(welcome);
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody Employee user) {
    loginService.saveUser(user);
    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> finalLogin(@RequestBody Employee loginUser,
                                                        HttpSession session) {
    try {
      Employee authenticatedUser = loginService.authenticate(loginUser.getEmployeeID(),
              loginUser.getPassword());
      log.info("Authenticated user: " + authenticatedUser);
      if (authenticatedUser != null) {
        session.setAttribute("employeeID", authenticatedUser.getEmployeeID());
        session.setAttribute("role", authenticatedUser.getRole());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Login successful. Session created.");
        responseBody.put("role", authenticatedUser.getRole());
        return ResponseEntity.ok(responseBody);
      } else {
        // Handle invalid credentials
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Invalid username or password"));
      }
    } catch (InvalidCredentialsException e) {
      log.error("Invalid credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Collections.singletonMap("error", "Invalid username or password"));
    }
    // Add any other necessary handling here (e.g., return an error response).
  }
   @PutMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Employee resetUser){
      try {

        int empId = resetUser.getEmployeeID();
        String newPwd = resetUser.getPassword();
        String updatedPassword = loginService.updatePassword(empId, newPwd);
        log.info("Successfully updated the password");
        return ResponseEntity.ok(updatedPassword);
      }catch(UserNotFoundException | PasswordValidationException e)
      {
        log.error("Error resetting Password : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

      }

    }

}