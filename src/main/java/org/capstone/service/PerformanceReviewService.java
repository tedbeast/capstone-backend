package org.capstone.service;


import org.capstone.Main;
import org.capstone.entity.PerformanceReview;
import org.capstone.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service
public class PerformanceReviewService {
    PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
        PerformanceReview p = new PerformanceReview();
        p.setGoalType("temp");
        p.setWeight(10);
        p.setEmployeeComments("temp1");
        this.performanceReviewRepository.save(p);

    }

    public List<PerformanceReview> getAllPerformanceReview() {
        Main.logger.info("Performance Review Get: Attempting to get all performance reviews.");
        return performanceReviewRepository.findAll();
    }

    public List<PerformanceReview> getAllPerformanceReviews(){
        Main.logger.info("logging method execution: PerformanceReviewService.getAllPerformanceReviews");
        Main.logger.info("PerformanceReviewService.getAllPerformanceReviews: Performance Review list successfully retrieved.");
        return performanceReviewRepository.findAll();
    }

    public PerformanceReview employeeAddComments(int employeeID, int performanceReviewID, PerformanceReview p){
        Optional<PerformanceReview> optional = performanceReviewRepository.findById(performanceReviewID);
        if(optional.isEmpty()){
            //dothiserrorthing
        }
        PerformanceReview performanceReview = optional.get();
        performanceReview.setEmployeeComments(p.employeeComments);
        performanceReviewRepository.save(performanceReview);
        return performanceReview;
    }

    public PerformanceReview managerAddComments(int employeeID, int performanceReviewID, PerformanceReview p){
        Optional<PerformanceReview> optional = performanceReviewRepository.findById(performanceReviewID);
        if(optional.isEmpty()){
            //dothiserrorthing
        }
        PerformanceReview performanceReview = optional.get();
        performanceReview.setManagerComments(p.managerComments);
        performanceReviewRepository.save(performanceReview);
        return performanceReview;
    }

}
