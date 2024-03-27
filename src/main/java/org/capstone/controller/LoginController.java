package org.capstone.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.capstone.entity.Employee;
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
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Invalid username or password"));
    }

  }

    @PutMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Employee resetUser){
        int empId = resetUser.getEmployeeID();
        String newPwd = resetUser.getPassword();
        String updatedPassword =   loginService.updatePassword(empId, newPwd);
        log.info("Successfully updated the password");
        return ResponseEntity.ok(updatedPassword);

    }

}