package org.capstone.util;

import jakarta.transaction.Transactional;
import org.capstone.entity.Employee;
import org.capstone.entity.Leave;
import org.capstone.entity.Manager;
import org.capstone.entity.Roles;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.LeaveRepository;
import org.capstone.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Seed basing employee, manager, and leave data upon startup of the application.
 * Assumes the hibernate database is in memory and these are being loaded upon initial startup. */
@Service
@Transactional
public class SetUpLeaveSeedData {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private LeaveRepository leaveRepository;



    //    @Autowired
    public String seedLeaveData(LeaveRepository leaveRepository, EmployeeRepository employeeRepository, ManagerRepository managerRepository) throws ParseException {

        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
        this.managerRepository = managerRepository;


        Employee employee1;
        Employee employee2;
        Employee employee3;
        Employee employee4;
        Employee employee5;
        Manager manager1;
        Manager manager2;


        employee1 = new Employee();

        // Define employee 1
        employee1.setPassword("myPassword1");
        employee1.setName("Employee 1 Manager 1");
        employee1.setJobTitle("Manager Level 1");
        employee1.setPhoneNumber("555-234-1234");
        employee1.setEmail("employee1@email.com");
        employee1.setAddressLine1("Address Line 1 value");
        employee1.setAddressLine2("Address Line 2 value");
        employee1.setCity("My City");
        employee1.setState("Virginia");
        employee1.setPostalCode("20003");
//        this.employee1.setBirthDate(datetimeFormatter.parse("1990-01-01 00:00:00.000Z"));
        employee1.setBirthDate(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("1990-01-01T00:00:00.000Z"));
        employee1.setAnniversary(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("1990-01-01T00:00:00.000Z"));
        employee1.setRole(Roles.MANAGER);

        manager1 = new Manager();

        manager1.setManagerID(1);

        employee2 = new Employee();

        employee2.setPassword("myPassword2");
        employee2.setName("Employee 2");
        employee2.setJobTitle("Employee Peon Level 1");
        employee2.setPhoneNumber("666-234-1235");
        employee2.setEmail("employee2@email.com");
        employee2.setAddressLine1("Address Line 12 value");
        employee2.setAddressLine2("Address Line 22 value");
        employee2.setCity("My City");
        employee2.setState("Virginia");
        employee2.setPostalCode("20003");
        employee2.setBirthDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("1990-01-01"));
        employee2.setAnniversary(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-08-15"));
        employee2.setRole(Roles.EMPLOYEE);
        employee2.setManager(manager1);

        employee3 = new Employee();

        employee3.setPassword("myPassword3");
        employee3.setName("Employee 3");
        employee3.setJobTitle("Employee Peon Level 1");
        employee3.setPhoneNumber("777-234-1235");
        employee3.setEmail("employee3@email.com");
        employee3.setAddressLine1("Address Line 13 value");
        employee3.setAddressLine2("Address Line 23 value");
        employee3.setCity("My City");
        employee3.setState("Virginia");
        employee3.setPostalCode("20003");
        employee3.setBirthDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("1994-01-01"));
        employee3.setAnniversary(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-08-15"));
        employee3.setRole(Roles.EMPLOYEE);
        employee3.setManager(manager1);

        employee4 = new Employee();

        employee4.setPassword("myPassword4");
        employee4.setName("Employee 4 Manager 2");
        employee4.setJobTitle("Manager Level 1");
        employee4.setPhoneNumber("888-234-1234");
        employee4.setEmail("employee4@email.com");
        employee4.setAddressLine1("Address Line 1 value");
        employee4.setAddressLine2("Address Line 2 value");
        employee4.setCity("My City");
        employee4.setState("Virginia");
        employee4.setPostalCode("20003");
        employee4.setBirthDate(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("1995-01-01T00:00:00.000Z"));
        employee4.setAnniversary(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").parse("2011-01-01T00:00:00.000Z"));
        employee4.setRole(Roles.MANAGER);

        manager2 = new Manager();

//        Assuming autogenerate is removed for manager.

        manager2.setManagerID(4);

        employee5 = new Employee();

        employee5.setPassword("myPassword5");
        employee5.setName("Employee 5");
        employee5.setJobTitle("Employee Peon Level 1");
        employee5.setPhoneNumber("999-234-1235");
        employee5.setEmail("employee5@email.com");
        employee5.setAddressLine1("Address Line 15 value");
        employee5.setAddressLine2("Address Line 25 value");
        employee5.setCity("My City");
        employee5.setState("Virginia");
        employee5.setPostalCode("20003");
        employee5.setBirthDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("1994-01-01"));
        employee5.setAnniversary(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-08-15"));
        employee5.setRole(Roles.EMPLOYEE);
        employee5.setManager(manager2);

//        save the records to the employee and manager repositories.

        this.employeeRepository.save(employee1);
        this.managerRepository.save(manager1);
        this.employeeRepository.save(employee2);
        this.employeeRepository.save(employee3);
        this.employeeRepository.save(employee4);
        this.managerRepository.save(manager2);
        this.employeeRepository.save(employee5);

//        Create leave request for employee 2 in pending state

        Leave leave1 = new Leave();
        leave1.setLeaveName("Sick");
        leave1.setStartDate(Timestamp.valueOf("2024-04-01 00:00:00"));
        leave1.setEndDate(Timestamp.valueOf("2024-04-02 00:00:00"));
        leave1.setActiveFlag(true);
        leave1.setAcceptedFlag(false);

//        Set the employee based upon those entered above (hardcoding out the wazoo)

        leave1.setEmployee(this.employeeRepository.findById(2).get());

        this.leaveRepository.save(leave1);

//        Create leave request for employee 2

        Leave leave2 = new Leave();
        leave2.setLeaveName("Paid Time Off");
        leave2.setStartDate(Timestamp.valueOf("2024-04-03 00:00:00"));
        leave2.setEndDate(Timestamp.valueOf("2024-04-07 00:00:00"));
        leave2.setActiveFlag(true);
        leave2.setAcceptedFlag(false);

//        Set the employee based upon those entered above (hardcoding out the wazoo)

        leave2.setEmployee(this.employeeRepository.findById(2).get());

        this.leaveRepository.save(leave2);

//        create an approved leave request

        Leave leave3 = new Leave();
        leave3.setLeaveName("Paid Time Off");
        leave3.setStartDate(Timestamp.valueOf("2024-04-03 00:00:00"));
        leave3.setEndDate(Timestamp.valueOf("2024-04-07 00:00:00"));
        leave3.setActiveFlag(false);
        leave3.setAcceptedFlag(true);

//        Set the employee based upon those entered above (hardcoding out the wazoo)

        leave3.setEmployee(this.employeeRepository.findById(3).get());

        this.leaveRepository.save(leave3);

//        Create a rejected leave request

        Leave leave4 = new Leave();
        leave4.setLeaveName("Paid Time Off");
        leave4.setStartDate(Timestamp.valueOf("2024-04-03 00:00:00"));
        leave4.setEndDate(Timestamp.valueOf("2024-04-07 00:00:00"));
        leave4.setActiveFlag(false);
        leave4.setAcceptedFlag(false);

//        Set the employee based upon those entered above (hardcoding out the wazoo)

        leave4.setEmployee(this.employeeRepository.findById(5).get());

        this.leaveRepository.save(leave4);

        return "Seed data added";

    }

}

