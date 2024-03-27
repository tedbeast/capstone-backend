package org.capstone.controller;

import org.capstone.entity.Leave;
import org.capstone.exception.LeaveException;
import org.capstone.exception.LeaveNotFoundException;
import org.capstone.repository.LeaveRepository;
import org.capstone.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class LeaveController {

    private final LeaveService leaveService;

    @Autowired
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /** Endpoints for localhost:9000/leave */

    // Endpoint for getting all leaves by employee
    @GetMapping(value="/employees/{employeeID}/leaves")
    public List<Leave> getLeaveByEmployeeID(int employeeID) throws LeaveException {
        return leaveService.getAllLeaveByEmployeeId(employeeID);
    }

    // Endpoint for getting all leaves by employee & accept status
    @GetMapping(value="/employees/{employeeID}/leaves/accepted")
    public List<Leave> getLeaveByEmployeeIdAndAccepted(int employeeID) throws LeaveException {
        return leaveService.getAllLeaveByEmployeeIdAndAcceptFlag(employeeID, true);
    }

    // Endpoint for getting all leaves by employee & rejected leaves
    @GetMapping(value = "/employees/{employeeID}/leaves/rejected")
    public List<Leave> getLeaveByEmployeeIdAndRejected(int employeeID) throws LeaveException {
        return leaveService.getAllLeaveByEmployeeIdAndAcceptFlag(employeeID, false); // Explicitly set acceptFlag to false
    }

    // Endpoint for getting all leaves by employee & active leaves
    @GetMapping(value = "/employees/{employeeID}/leaves/active")
    public List<Leave> getLeaveByEmployeeIdAndActive(int employeeID) throws LeaveException {
        return leaveService.getAllLeavesByEmployeeIdAndActiveFlag(employeeID, true); // Default active to true
    }

    // Endpoint for inactive leaves
    @GetMapping(value = "/employees/{employeeID}/leaves/inactive")
    public List<Leave> getLeaveByEmployeeIdAndInactive(int employeeID) throws LeaveException {
        return leaveService.getAllLeavesByEmployeeIdAndActiveFlag(employeeID, false); // Explicitly set active to false
    }


    @DeleteMapping("leaves/{leaveId}")
    public ResponseEntity<?> deleteLeave(@PathVariable int leaveId) {
        try {
            Leave leave = leaveService.deleteLeave(leaveId);
            return new ResponseEntity<>(leave, HttpStatus.OK);
        } catch (LeaveNotFoundException e) {
            // Even if product is not found, return 200 status - this is convention
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

    }


   @PostMapping(value = "/Leave")
    public ResponseEntity<Leave> addLeave(@RequestBody Leave leave) throws LeaveException {
        Leave newLeave = leaveService.addLeave(leave);
        return ResponseEntity.ok(newLeave);
   }
    @PostMapping(value = "/Leave/active")
    public List<Leave>  findAllLeaveByActiveFlag(@RequestBody boolean activeFlag) throws LeaveException {
        return leaveService.getAllLeavesByActiveStatus(activeFlag);
    }


 }
