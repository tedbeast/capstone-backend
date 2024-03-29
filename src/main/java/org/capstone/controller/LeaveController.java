package org.capstone.controller;

import org.capstone.entity.Leave;
import org.capstone.entity.LeaveResponse;
import org.capstone.exception.LeaveException;
import org.capstone.exception.LeaveFinancialException;
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


    /** Endpoints for localhost:????/leave */


    // Endpoint for getting all leaves
    @GetMapping(value = "/leave")
    public ResponseEntity<?> getLeave() throws LeaveException {
        try {
            List<Leave> l = leaveService.getAllLeaves();
            return new ResponseEntity<>(l, HttpStatus.OK);
        } catch (LeaveException e) {
            // Specific error response, returning a string.
            String errorResponse = "No leaves found: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for getting all leaves by employee
    @GetMapping(value = "/employee/{employeeID}/leave")
    public ResponseEntity<?> getLeaveByEmployeeID(@PathVariable int employeeID) throws LeaveException {
        try {
            List<Leave> leaves = leaveService.getAllLeaveByEmployeeId(employeeID);
            return ResponseEntity.ok(leaves);
        } catch (LeaveException e) {
            // Specific error response, returning a string.
            String errorResponse = "No leaves found for a given employee: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for getting all leaves by employee & accept status
    @GetMapping(value = "/employee/{employeeID}/leave/accepted")
    public ResponseEntity<?> getLeaveByEmployeeIdAndAccepted(@PathVariable int employeeID) throws LeaveException {
        try {
            List<Leave> leaves = leaveService.getAllLeaveByEmployeeIdAndAcceptFlag(employeeID, true);
            return ResponseEntity.ok(leaves);
        } catch (LeaveException e) {
            // Specific error response, returning a string.
            String errorResponse = "No approved leaves found: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for getting all leaves by employee & rejected leaves
    @GetMapping(value = "/employee/{employeeID}/leave/rejected")
    public ResponseEntity<?> getLeaveByEmployeeIdAndRejected(@PathVariable int employeeID) throws LeaveException {
        try {
            List<Leave> leaves =  leaveService.getAllLeaveByEmployeeIdAndAcceptFlag(employeeID, false); // Explicitly set acceptFlag to false
            return ResponseEntity.ok(leaves);
        } catch (LeaveException e) {
            // Specific error response, returning a string.
            String errorResponse = "No non-approved leaves found: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for getting all leaves by employee & active leaves
    @GetMapping(value = "/employee/{employeeID}/leave/active")
    public ResponseEntity<?> getLeaveByEmployeeIdAndActive(@PathVariable int employeeID) throws LeaveException {
        try {
            List<Leave> leaves = leaveService.getAllLeavesByEmployeeIdAndActiveFlag(employeeID, true); // Default active to true
            return ResponseEntity.ok(leaves);
        } catch (LeaveException e) {
            // Specific error response, returning a string.
            String errorResponse = "No pending leaves found: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for inactive leaves
    @GetMapping(value = "/employee/{employeeID}/leave/inactive")
    public ResponseEntity<?> getLeaveByEmployeeIdAndInactive(@PathVariable int employeeID) throws LeaveException {
        try {
            List<Leave> leaves = leaveService.getAllLeavesByEmployeeIdAndActiveFlag(employeeID, false); // Explicitly set active to false
            return ResponseEntity.ok(leaves);
        } catch (LeaveException e) {
            // Specific error response, returning a string.
            String errorResponse = "No non-pending leaves found: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/employee/{employeeId}/leave/{id}")
    public ResponseEntity<?> updateLeave(@RequestBody Leave l, @PathVariable int employeeId, @PathVariable int id) {
        try {
            Leave updatedLeave = leaveService.updateLeave(id, l);
            return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
        } catch (LeaveNotFoundException e) {
            // Specific error response, returning a string.
            String errorResponse = "Leave is not updated: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }

    }

    /**Create a custom response object that encapsulates the status code, status message,
     * and the updated leave object (if applicable) to be sent to front end */
    @PutMapping("/leave/{id}")
    public ResponseEntity<LeaveResponse> updateLeaveById(@RequestBody Leave l, @PathVariable int id) {
        try {
            Leave updatedLeave = leaveService.updateLeaveById(id, l);
            LeaveResponse response = new LeaveResponse(HttpStatus.OK, "Leave updated successfully", updatedLeave);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (LeaveNotFoundException | LeaveFinancialException e) {
            LeaveResponse response = new LeaveResponse(HttpStatus.BAD_REQUEST, "Leave is not updated: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


//    // Update leaves after manager approves/rejects them
//    @PutMapping("/leave/{id}")
//    public ResponseEntity<?> updateLeaveByManager(@RequestBody Leave l, @PathVariable int id, @RequestParam boolean acceptedFlag) {
//        try {
//            Leave updatedLeave = leaveService.updateLeaveByManager(id, l, acceptedFlag);
//            return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
//        } catch (LeaveNotFoundException e) {
//            // Specific error response, returning a string.
//            String errorResponse = "Leave is not updated: " + e.getMessage();
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//        }
//
//    }

    @DeleteMapping("leave/{leaveId}")
    public ResponseEntity<?> deleteLeave(@PathVariable int leaveId) {
        try {
            Leave leave = leaveService.deleteLeave(leaveId);
            return new ResponseEntity<>("Leave successfully deleted", HttpStatus.OK);
        } catch (LeaveNotFoundException e) {
            // Even if product is not found, return 200 status - this is convention
            String errorResponse = "Leave is not deleted: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.OK);
        }
    }


    @PostMapping(value = "/leave")
    public ResponseEntity<?> addLeave(@RequestBody Leave leave) throws LeaveException {
        try{
            Leave newLeave = leaveService.addLeave(leave);
            return new ResponseEntity<>(newLeave, HttpStatus.CREATED);
        } catch(LeaveException e){
            System.err.println("Error adding leave: " + e.getMessage());

            // Specific error response, returning a string.
            String errorResponse = "Leave not added: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/leave/active")
    public List<Leave>  findAllLeaveByActiveFlag(@RequestBody boolean activeFlag) throws LeaveException {
        return leaveService.getAllLeavesByActiveStatus(activeFlag);
    }


    /** Endpoints for localhost:????/payroll */


}
