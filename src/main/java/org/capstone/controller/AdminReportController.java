package org.capstone.controller;

import org.capstone.service.AdminReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class AdminReportController {
    AdminReportService adminReportService;

    @Autowired
    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }
    @GetMapping("/average-rating-per-goal-type")
    public List<Object[]> getAverageRatingPerGoalType() {
        return adminReportService.findAverageRatingPerGoalType();
    }
}
