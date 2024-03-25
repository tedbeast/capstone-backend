package org.capstone.service;

import org.capstone.Main;
import org.capstone.entity.PerformanceReview;
import org.capstone.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceReviewService {
    PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
    }

    public List<PerformanceReview> getAllPerformanceReviews(){
        Main.logger.info("logging method execution: PerformanceReviewService.getAllPerformanceReviews");
        Main.logger.info("PerformanceReviewService.getAllPerformanceReviews: Performance Review list successfully retrieved.");
        return performanceReviewRepository.findAll();
    }
}
