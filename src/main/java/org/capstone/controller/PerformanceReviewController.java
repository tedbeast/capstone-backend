package org.capstone.controller;

import org.capstone.entity.PerformanceReview;
import org.capstone.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceReview() {
        List<PerformanceReview> performanceReviewList = performanceReviewService.getAllPerformanceReview();
        return new ResponseEntity<>(performanceReviewList, HttpStatus.OK);
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


}

