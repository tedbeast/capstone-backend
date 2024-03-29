package org.capstone.controller;

import org.capstone.service.AdminReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class AdminReportController {
    AdminReportService adminReportService;


    @Autowired
    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }
    @GetMapping("average-rating-per-goal-type")
    public List<Object[]> getAverageRatingPerGoalType() {
        return adminReportService.findAverageRatingPerGoalType();
    }

    @GetMapping("average-rating")
    public List<Object[]> getAverageRatingPerEmployee() {
        return adminReportService.getAverageRatingPerEmployee();
    }

    @GetMapping("/employees/lowRating")
    public List<Object[]> getNumberOfEmployeesWithRatingUnderThree() {
        return adminReportService.getNumberOfEmployeesWithRatingUnderThree();
    }




    @GetMapping("/reviews/count")
    public ResponseEntity<Map<Integer, Long>> getCountOfReviewsPerEmployee() {
        Map<Integer, Long> countOfReviews = adminReportService.getCountOfReviewsPerEmployee();
        return new ResponseEntity<>(countOfReviews, HttpStatus.OK);
    }


}
