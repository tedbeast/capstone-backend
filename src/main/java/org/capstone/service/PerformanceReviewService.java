package org.capstone.service;

import org.capstone.Main;

import org.capstone.entity.Employee;
import org.capstone.entity.PerformanceReview;
import org.capstone.repository.PerformanceReviewRepository;

import org.capstone.entity.*;
import org.capstone.exception.*;
import org.capstone.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PerformanceReviewService {
    PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
    }

    public List<PerformanceReview> getAllPerformanceReview() {
        Main.logger.info("Performance Review Get: Attempting to get all performance reviews.");
        return performanceReviewRepository.findAll();
    }


    public List<Employee> getAllEmployeeByManagerID(int managerID){
        return performanceReviewRepository.findEmployeeByManagerID(managerID);
    }

    public List<PerformanceReview> getAllPerformanceReviewsByEmployeeID(int employeeID) throws PerformanceReviewException {
        Main.logger.info("Performance Review Get: Attempting to get all performance reviews by Employee ID.");
        List<PerformanceReview> performanceReview = performanceReviewRepository.findPerformanceReviewByEmployeeID(employeeID);
        if(performanceReview.isEmpty()){
            Main.logger.warn("Performance Review Get: Performance Review by Employee ID Not Found");
            throw new PerformanceReviewException("Performance Review by Employee ID Not Found");
        }
        else {
            return performanceReview;
        }
    }

    public List<PerformanceReview> getAllPerformanceByManager(int id){
        Main.logger.info("logging method execution: PerformanceReviewService.getAllPerformanceByManager");
        Main.logger.info("PerformanceReviewService.getAllPerformanceByManager: Performance Review list successfully retrieved.");
        return performanceReviewRepository.findPerformanceReviewByManagerID(id);
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
