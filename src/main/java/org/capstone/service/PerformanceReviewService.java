package org.capstone.service;

import org.capstone.entity.PerformanceReview;
import org.capstone.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerformanceReviewService {
    PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
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
