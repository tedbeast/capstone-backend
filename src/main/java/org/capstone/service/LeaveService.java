package org.capstone.service;

import jakarta.transaction.Transactional;
import org.capstone.Main;
import org.capstone.entity.Employee;
import org.capstone.entity.Leave;
import org.capstone.entity.Roles;
import org.capstone.exception.LeaveException;
import org.capstone.exception.LeaveFinancialException;
import org.capstone.entity.Manager;
import org.capstone.exception.LeaveManagerNotFoundException;
import org.capstone.exception.LeaveNotFoundException;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.LeaveRepository;
import org.capstone.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.*;

@Service
@Transactional

public class LeaveService {
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository, ManagerRepository managerRepository, RestTemplate restTemplate) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
        this.restTemplate = restTemplate;
    }

    //Get All Leaves
    public List<Leave> getAllLeaves() throws LeaveException {
        Main.logger.info("Getting leaves by employee");
        List <Leave> l = leaveRepository.findAll();
        if (l.isEmpty()) {
            throw new LeaveException("No leaves found");
        }
        return l;
    }


    //Get All Leaves by Employee ID
    public List<Leave> getAllLeaveByEmployeeId(int employeeID) throws LeaveException {
        Main.logger.info("Getting leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeID(employeeID);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves found for an employee with this id: " + employeeID);
        }
        return l;
    }

    //Get All Leaves by Employee ID & Accepted Flag
    public List<Leave> getAllLeaveByEmployeeIdAndAcceptFlag(int employeeID, boolean acceptedFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeIDAndAcceptedFlag(employeeID, acceptedFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Accept/Reject Flag are found: " + employeeID);
        }
        return l;
    }

    //Get All Leaves by Employee ID & Active Flag
    public List<Leave> getAllLeavesByEmployeeIdAndActiveFlag(int employeeID, boolean activeFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeIDAndActiveFlag(employeeID, activeFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Active Flag are found: " + employeeID);
        }
        return l;
    }

    public List<Leave> getAllEmployeeLeavesForManager(int managerID) throws LeaveManagerNotFoundException {
        Main.logger.info("Getting all employee leaves for manager ID " + managerID);
        // Get the employees who report to this manager
        Optional<Manager> optionalManager = managerRepository.findById(managerID);
        List<Leave> l = new ArrayList<Leave>();
        if (optionalManager.isPresent()) {
            l = leaveRepository.findAllEmployeeLeaveByManager(managerID);
            if (l.isEmpty()) {
                Main.logger.info("The manager ID " + managerID + " does not have any leave requests for supervised employees.");
            }
        } else {
            Main.logger.warn("The manager ID, " + managerID + " does not have a Manager record");
            throw new LeaveManagerNotFoundException("Manager ID supplied does not have any Manager record");
        }
        return l;
    }

    public List<Leave> getEmployeeLeavesForManagerByStatus(int managerID, boolean activeFlag, boolean acceptedFlag) throws LeaveManagerNotFoundException {
        Main.logger.info("Getting all employee leaves for a manager");
        // Get the employees who report to this manager
        Optional<Manager> optionalManager = managerRepository.findById(managerID);
        List<Leave> l = new ArrayList<Leave>();
        if (optionalManager.isPresent()) {
            l = leaveRepository.findEmployeeLeaveByManagerByStatusFlags(managerID, activeFlag, acceptedFlag);
            if (l.isEmpty()) {
                Main.logger.info("The manager ID " + managerID + " does not have any leave requests for employees for the requested status. accepted_flag: " + acceptedFlag + ", active_flag: " + activeFlag);
//                throw new LeaveException("The manager does not have any leave requests for supervised employees.");
            }
        } else {
            Main.logger.warn("Employee ID " + managerID + " does not have a Manager record");
            throw new LeaveManagerNotFoundException("Employee ID supplied does not have a Manager record");
        }
        return l;
    }


    public Leave updateLeaveById(int Id, Leave updatedLeave) throws LeaveNotFoundException, LeaveFinancialException {
        Main.logger.info("Updating Leave with ID: "+Id);

        // leaving to show the update of name in case we cause problems with the merge

//    public Leave updateLeave(int Id, Leave updatedLeave) throws LeaveNotFoundException {
//        Main.logger.info("Updating Leave with ID: {}, id");

        Optional<Leave> optionalLeave = leaveRepository.findById(Id);
        Optional<Employee> employeeOptional =
        employeeRepository.findById(Id);


        // Validate leave existence
         leaveRepository.findById(Id)
                .orElseThrow(() -> new LeaveNotFoundException("Leave Not Found"));

        // Role-based logic
        System.out.println("Employee ID: " +employeeOptional.get().getEmployeeID());
        System.out.println("Role: " +employeeOptional.get().getRole());
        if (employeeOptional.get().getRole() == Roles.MANAGER) {
            System.out.println("Role: " +employeeOptional.get().getRole());
            // Call the payroll service
            try {
                //If the 2nd API call doesn't throw an exception, we are assuming success;  otherwise error
                callPayrollService(updatedLeave);       //call 2nd API
                updatedLeave.setAcceptedFlag(true);     //Set accepted if payroll service call is successful
                System.out.println("Payroll service call successful, leave approved.");
            } catch (Exception | LeaveFinancialException e) {
                updatedLeave.setAcceptedFlag(false);    // Set not accepted for any other exception
                System.out.println("Payroll service call failed, leave not approved: " + e.getMessage());
                throw e;                                // Re-throw the exception without modifying the message
            }
        } else {
            // Employee leave updates
            updatedLeave.setActiveFlag(true);
            updatedLeave.setAcceptedFlag(false);
        }

        return leaveRepository.save(updatedLeave);
    }

    // Call to the 2nd API: Financial API service -- it returns successful/unsuccessful response
    public void callPayrollService(Leave leave) throws LeaveFinancialException {
        String url = "http://localhost:9002/payroll";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Set the content type to JSON

            // Prepare a minimal request body
            Map<String, Object> requestBody = new HashMap<>();

            // Create a data for the POST request body: approvalDate
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); // Adjust format to match the payroll API one
            String formattedStartDate = dateFormat.format(leave.getStartDate());
            System.out.println("formattedStartDate: " + formattedStartDate);
            requestBody.put("approvalDate", formattedStartDate);         // startDate returns a Date object

            // Create an empty ArrayList for the POST request body: payrollPeriods
            List<Object> emptyPayrollPeriods = new ArrayList<>();
            requestBody.put("payrollPeriods", emptyPayrollPeriods);

            // Create a POST request entity with the request body
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the POST request and get the response
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

            // Check if the response status code indicates success
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Payroll service call successful");
            } else {
                System.out.println("Payroll service call failed with status code: " + response.getStatusCode());
                throw new LeaveFinancialException("Payroll service call failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("Payroll service call failed: " + e.getMessage());
            throw new LeaveFinancialException("Payroll service call failed: " + e.getMessage());
        }
    }


    // Update the active flag for a leave
    public Leave updateActiveFlag(int Id, boolean isActive) throws LeaveException {
        Leave leaveToUpdate = leaveRepository.findById(Id)
                .orElseThrow(() -> new LeaveException("Leave not found with ID: " + Id));

        leaveToUpdate.setActiveFlag(isActive);
        return leaveRepository.save(leaveToUpdate);
    }

    // Update the accepted flag for a leave
    public Leave updateAcceptedFlag(int Id, boolean isAccepted) throws LeaveException {
        Leave leaveToUpdate = leaveRepository.findById(Id)
                .orElseThrow(() -> new LeaveException("Leave not found with ID: " + Id));

        leaveToUpdate.setAcceptedFlag(isAccepted);
        return leaveRepository.save(leaveToUpdate);
    }



    public Leave deleteLeave(int leaveId) throws LeaveNotFoundException {
        Main.logger.info("Deleting leave");
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        Leave leaveToDelete;
        if(optionalLeave.isEmpty()){
            throw new LeaveNotFoundException("Leave with id " + leaveId + " not found");
        }else{
            leaveToDelete = optionalLeave.get();
        }
        leaveRepository.delete(leaveToDelete);
        return leaveToDelete;
    }



   public Leave addLeave(Leave leave) throws LeaveException {
       Main.logger.info("Attempting to add a new leave: " + leave);
       //check for duplicate leaves
       List<Leave> existingLeaves = leaveRepository.findByIdAndLeaveNameAndStartDateAndEndDate
               (leave.getId(),leave.getLeaveName(),leave.getStartDate(), leave.getEndDate());
       if (!existingLeaves.isEmpty()) {
           throw new LeaveException("Leave with same detail already Exists");
       }

       // Validate leave details
       if (leave.getLeaveName() == null || leave.getLeaveName().isEmpty()) {
           throw new LeaveException("Leave name is required");
       }
       if (leave.getStartDate() == null || leave.getEndDate() == null) {
           throw new LeaveException("Start date and end date are required");
       }
       if (leave.getEndDate().before(leave.getStartDate())) {
           throw new LeaveException("End date must be after Start Date");
       }
       //We can add this if we want to check if an employee is assigned with a manager
       //if (leave.getEmployee() == null || leave.getEmployee().getManager() == null) {
        //  throw new LeaveException("Employee must have a Manager assigned");
       // }

        leave.setAcceptedFlag(false);            // new leave is set to Not Approved
        leave.setActiveFlag(true);               // new leave is set to Active

        leaveRepository.save(leave);
        Main.logger.info("New Leave added: " + leave);
        return leaveRepository.save(leave);


    }
    public List<Leave> getAllLeavesByActiveStatus(boolean activeStatus) throws LeaveException {
        Main.logger.info("Getting leaves by active status");
        List<Leave> leaves = leaveRepository.findByActiveFlag(activeStatus);
        if (leaves.isEmpty()) {
            throw new LeaveException("No leaves with active status " + activeStatus + " are found");
        }
        return leaves;
    }




}



