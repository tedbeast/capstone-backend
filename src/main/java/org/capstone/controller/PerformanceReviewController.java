package org.capstone.controller;

import org.capstone.entity.PerformanceReview;
import org.capstone.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class PerformanceReviewController {
    PerformanceReviewService performanceReviewService;

    @Autowired
    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
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
