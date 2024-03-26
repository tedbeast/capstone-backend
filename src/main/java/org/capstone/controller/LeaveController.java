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
    @GetMapping(value="/employees/{employeeId}/leaves")
    public List<Leave> getLeaveByEmployeeId(int employeeId) throws LeaveException {
        return leaveService.getAllLeaveByEmployeeId(employeeId);
    }

    // Endpoint for getting all leaves by employee & accept status
    @GetMapping(value="/employees/{employeeId}/leaves/accepted")
    public List<Leave> getLeaveByEmployeeIdAndAccepted(int employeeId) throws LeaveException {
        return leaveService.getAllLeaveByEmployeeIdAndAcceptFlag(employeeId, true);
    }

    // Endpoint for getting all leaves by employee & rejected leaves
    @GetMapping(value = "/employees/{employeeId}/leaves/rejected")
    public List<Leave> getLeaveByEmployeeIdAndRejected(int employeeId) throws LeaveException {
        return leaveService.getAllLeaveByEmployeeIdAndAcceptFlag(employeeId, false); // Explicitly set acceptFlag to false
    }

    // Endpoint for getting all leaves by employee & active leaves
    @GetMapping(value = "/employees/{employeeId}/leaves/active")
    public List<Leave> getLeaveByEmployeeIdAndActive(int employeeId) throws LeaveException {
        return leaveService.getAllLeavesByEmployeeIdAndActiveFlag(employeeId, true); // Default active to true
    }

    // Endpoint for inactive leaves
    @GetMapping(value = "/employees/{employeeId}/leaves/inactive")
    public List<Leave> getLeaveByEmployeeIdAndInactive(int employeeId) throws LeaveException {
        return leaveService.getAllLeavesByEmployeeIdAndActiveFlag(employeeId, false); // Explicitly set active to false
    }


    @PutMapping("/employees/{employeeId}/leaves/{id}")
    public ResponseEntity<Leave> updateLeave(@RequestBody Leave l, @PathVariable int employeeId, @PathVariable int id) {
        try {
            Leave updatedLeave = leaveService.updateLeave(id, l);
            return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
        } catch (LeaveNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
