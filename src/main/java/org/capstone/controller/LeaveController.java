package org.capstone.controller;

import org.capstone.entity.Leave;
import org.capstone.exception.LeaveException;
import org.capstone.repository.LeaveRepository;
import org.capstone.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
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

   @PostMapping("/add")
    public ResponseEntity<Leave> addLeave(@RequestBody Leave leave){
        Leave newLeave = leaveService.addLeave(leave);
        return ResponseEntity.ok(newLeave);
   }


}
