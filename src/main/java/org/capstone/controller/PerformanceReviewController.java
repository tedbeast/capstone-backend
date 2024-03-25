package org.capstone.controller;

import org.capstone.entity.PerformanceReview;
import org.capstone.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class PerformanceReviewController {
    PerformanceReviewService performanceReviewService;

    @Autowired
    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @GetMapping(value = "/performance")
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceReviews(){
        List<PerformanceReview> pr = performanceReviewService.getAllPerformanceReviews();
        return new ResponseEntity<>(pr, HttpStatus.OK);
    }
}

