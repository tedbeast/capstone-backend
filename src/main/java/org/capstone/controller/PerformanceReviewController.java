package org.capstone.controller;

import org.capstone.entity.Employee;
import com.sun.net.httpserver.HttpsServer;
import org.capstone.entity.PerformanceReview;
import org.capstone.exception.PerformanceReviewException;
import org.capstone.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.*;



import java.util.*;


@CrossOrigin
@RestController
public class PerformanceReviewController {
    PerformanceReviewService performanceReviewService;

    @Autowired
    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @GetMapping("/performanceReview")
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceReview(){
        List<PerformanceReview> performanceReviewList = performanceReviewService.getAllPerformanceReview();
        return new ResponseEntity<>(performanceReviewList, HttpStatus.OK);
    }


    @GetMapping("/employee/{managerID}")
    public ResponseEntity<List<Employee>> getEmployeeByManagerId(@PathVariable int managerID) {
        List<Employee> employeeList = performanceReviewService.getAllEmployeeByManagerID(managerID);
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("employee/{empId}/performanceReview")
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceReview(@PathVariable int empId) {
        try{
            List<PerformanceReview> performanceReviewList = performanceReviewService.getAllPerformanceReviewsByEmployeeID(empId);
            return new ResponseEntity<>(performanceReviewList, HttpStatus.OK);
        }
        catch (PerformanceReviewException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "performance", params = {"managerID"})
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceByManager(@RequestParam("managerID") int id){
        List<PerformanceReview> pr = performanceReviewService.getAllPerformanceByManager(id);
        return new ResponseEntity<>(pr, HttpStatus.OK);

    }

    @PutMapping("employee/{empId}/performanceReview/{prId}/update")
    public ResponseEntity<?> EmployeeComments(@RequestBody PerformanceReview p, @PathVariable("empId") int employeeID, @PathVariable("prId") int performanceReviewID) {
        PerformanceReview performanceReview = performanceReviewService.employeeAddComments(employeeID, performanceReviewID, p);
        return new ResponseEntity<PerformanceReview>(performanceReview, HttpStatus.OK);
    }

    @PutMapping("employee/{empId}/performanceReview/{prId}/managerReview")
    public ResponseEntity<?> managerComments(@RequestBody PerformanceReview p, @PathVariable("empId") int employeeID, @PathVariable("prId") int performanceReviewID) {
        PerformanceReview performanceReview = performanceReviewService.managerAddComments(employeeID, performanceReviewID, p);
        return new ResponseEntity<PerformanceReview>(performanceReview, HttpStatus.OK);
    }

    @PostMapping("/employee/{empId}/performanceReview")
    public ResponseEntity<?> addPerformanceReview(@RequestBody PerformanceReview p, @PathVariable("empId") int employeeID) 
        {
            performanceReviewService.addPerformanceReview(employeeID, p);
            return new ResponseEntity<PerformanceReview>(p, HttpStatus.OK);
    }

}

