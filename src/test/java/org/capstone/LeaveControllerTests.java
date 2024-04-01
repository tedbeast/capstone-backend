package org.capstone;

import jakarta.transaction.Transactional;
import org.capstone.controller.LeaveController;
import org.capstone.entity.Employee;
import org.capstone.entity.Leave;
import org.capstone.entity.Manager;
import org.capstone.entity.Roles;
import org.capstone.exception.LeaveException;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.LeaveRepository;
import org.capstone.repository.ManagerRepository;
import org.capstone.repository.PerformanceReviewRepository;
import org.capstone.service.LeaveService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LeaveControllerTests {


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private LeaveController leaveController;

    @Autowired
    private LeaveService leaveService;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;
    private Manager manager1;
    private Manager manager2;

    @BeforeEach
    public void setUp() throws ParseException {

        this.employee1 = new Employee();

        // Define employee 1
        this.employee1.setPassword("myPassword1");
        this.employee1.setName("Employee 1 Manager 1");
        this.employee1.setJobTitle("Manager Level 1");
        this.employee1.setPhoneNumber("555-234-1234");
        this.employee1.setEmail("employee1@email.com");
        this.employee1.setAddressLine1("Address Line 1 value");
        this.employee1.setAddressLine2("Address Line 2 value");
        this.employee1.setCity("My City");
        this.employee1.setState("Virginia");
        this.employee1.setPostalCode("20003");
//        this.employee1.setBirthDate(datetimeFormatter.parse("1990-01-01 00:00:00.000Z"));
        this.employee1.setBirthDate(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("1990-01-01T00:00:00.000Z"));
        this.employee1.setAnniversary(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("1990-01-01T00:00:00.000Z"));
        this.employee1.setRole(Roles.MANAGER);

        this.manager1 = new Manager();

        this.manager1.setManagerID(1);

        this.employee2 = new Employee();

        this.employee2.setPassword("myPassword2");
        this.employee2.setName("Employee 2");
        this.employee2.setJobTitle("Employee Peon Level 1");
        this.employee2.setPhoneNumber("666-234-1235");
        this.employee2.setEmail("employee2@email.com");
        this.employee2.setAddressLine1("Address Line 12 value");
        this.employee2.setAddressLine2("Address Line 22 value");
        this.employee2.setCity("My City");
        this.employee2.setState("Virginia");
        this.employee2.setPostalCode("20003");
        this.employee2.setBirthDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("1990-01-01"));
        this.employee2.setAnniversary(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-08-15"));
        this.employee2.setRole(Roles.EMPLOYEE);
        this.employee2.setManager(manager1);

        this.employee3 = new Employee();

        this.employee3.setPassword("myPassword3");
        this.employee3.setName("Employee 3");
        this.employee3.setJobTitle("Employee Peon Level 1");
        this.employee3.setPhoneNumber("777-234-1235");
        this.employee3.setEmail("employee3@email.com");
        this.employee3.setAddressLine1("Address Line 13 value");
        this.employee3.setAddressLine2("Address Line 23 value");
        this.employee3.setCity("My City");
        this.employee3.setState("Virginia");
        this.employee3.setPostalCode("20003");
        this.employee3.setBirthDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("1994-01-01"));
        this.employee3.setAnniversary(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-08-15"));
        this.employee3.setRole(Roles.EMPLOYEE);
        this.employee3.setManager(manager1);

        this.employee4 = new Employee();

        this.employee4.setPassword("myPassword4");
        this.employee4.setName("Employee 4 Manager 2");
        this.employee4.setJobTitle("Manager Level 1");
        this.employee4.setPhoneNumber("888-234-1234");
        this.employee4.setEmail("employee4@email.com");
        this.employee4.setAddressLine1("Address Line 1 value");
        this.employee4.setAddressLine2("Address Line 2 value");
        this.employee4.setCity("My City");
        this.employee4.setState("Virginia");
        this.employee4.setPostalCode("20003");
        this.employee4.setBirthDate(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("1995-01-01T00:00:00.000Z"));
        this.employee4.setAnniversary(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("2011-01-01T00:00:00.000Z"));
        this.employee4.setRole(Roles.MANAGER);

        this.manager2 = new Manager();

        this.manager2.setManagerID(2);

        this.employee5 = new Employee();

        this.employee5.setPassword("myPassword5");
        this.employee5.setName("Employee 5");
        this.employee5.setJobTitle("Employee Peon Level 1");
        this.employee5.setPhoneNumber("999-234-1235");
        this.employee5.setEmail("employee5@email.com");
        this.employee5.setAddressLine1("Address Line 15 value");
        this.employee5.setAddressLine2("Address Line 25 value");
        this.employee5.setCity("My City");
        this.employee5.setState("Virginia");
        this.employee5.setPostalCode("20003");
        this.employee5.setBirthDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("1994-01-01"));
        this.employee5.setAnniversary(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-08-15"));
        this.employee5.setRole(Roles.EMPLOYEE);
        this.employee5.setManager(manager2);

//        save the records to the employee and manager repositories.

        employeeRepository.save(this.employee1);
        managerRepository.save(this.manager1);
        employeeRepository.save(this.employee2);
        employeeRepository.save(this.employee3);
        employeeRepository.save(this.employee4);
        managerRepository.save(this.manager2);
        employeeRepository.save(this.employee5);

    }


    @AfterEach
    public void tearDown() {
        employeeRepository.deleteAll();
        managerRepository.deleteAll();
        this.manager1 = null;
        this.manager2 = null;
        this.employee1 = null;
        this.employee2 = null;
        this.employee3 = null;
        this.employee4 = null;
        this.employee5 = null;

    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void givenEmployeeManagerSeedDataAdded() {

        // IDs are hardcoded.  In hindsight, maybe I should do this as a count or records since the number of employees and managers
        // created to test the leave repository may need more or less than configured.


        assertTrue(employeeRepository.findById(1).isPresent());
        assertTrue(employeeRepository.findById(2).isPresent());
        assertTrue(employeeRepository.findById(3).isPresent());
        assertTrue(employeeRepository.findById(4).isPresent());
        assertTrue(employeeRepository.findById(5).isPresent());

        assertTrue(managerRepository.findById(1).isPresent());
        assertTrue(managerRepository.findById(2).isPresent());
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void givenBasicLeaveRecordConfirmAdded() throws ParseException, LeaveException {
        Leave leave1 = new Leave();
        leave1.setLeaveName("Sick");
        leave1.setStartDate(Timestamp.valueOf("2024-04-01 00:00:00"));
        leave1.setEndDate(Timestamp.valueOf("2024-04-05 00:00:00"));
        leave1.setActiveFlag(true);
        leave1.setAcceptedFlag(false);
        leave1.setEmployee(this.employeeRepository.findById(2).get());

        Leave newLeave1 = this.leaveService.addLeave(leave1);

        ResponseEntity<?> leaveResponseEntity =  leaveController.addLeave(leave1);


        assertEquals(leave1.getLeaveName(), newLeave1.getLeaveName());
        assertEquals(leave1.getStartDate(), newLeave1.getStartDate());
        assertEquals(leave1.getEndDate(), newLeave1.getEndDate());
        assertEquals(leave1.isActiveFlag(), newLeave1.isActiveFlag());
        assertEquals(leave1.isAcceptedFlag(),newLeave1.isAcceptedFlag());

    }
}
