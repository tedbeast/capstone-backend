package org.capstone.controller;

import org.capstone.entity.*;
import org.capstone.exception.PerformanceReviewException;
import org.capstone.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    //returns all performance reviews for all employees
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

    //returns all performance reviews for an employee
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

    //returns all goals for an employee
    @GetMapping("employee/{empId}/goals")
    public ResponseEntity<List<Goal>> getGoals(@PathVariable int empId) {
        try{
            List<Goal> goalList = performanceReviewService.getAllGoalsByEmployeeID(empId);
            return new ResponseEntity<>(goalList, HttpStatus.OK);
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

    @PutMapping("employee/{empId}/performanceReview/{prId}/{goalID}/update")
    public ResponseEntity<?> EmployeeComments(@RequestBody Goal g, @PathVariable("empId") int employeeID, @PathVariable("prId") int performanceReviewID, @PathVariable("goalID") int goalID) {
        Goal goal = performanceReviewService.employeeAddComments(employeeID, performanceReviewID, g, goalID);
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @PutMapping("employee/{empId}/performanceReview/{prId}/managerReview")
    public ResponseEntity<?> managerComments(@RequestBody PerformanceReview p, @PathVariable("empId") int employeeID, @PathVariable("prId") int performanceReviewID) {
        PerformanceReview performanceReview = performanceReviewService.managerAddComments(employeeID, performanceReviewID, p);
        return new ResponseEntity<PerformanceReview>(performanceReview, HttpStatus.OK);
    }

    //posts a new performance review for an employee
    @PostMapping("/employee/{empId}/performanceReview")
    public ResponseEntity<?> addPerformanceReview(@PathVariable("empId") int employeeID) {
        try {
            PerformanceReview performanceReview = performanceReviewService.addPerformanceReview(employeeID);
            return new ResponseEntity<PerformanceReview>(performanceReview, HttpStatus.OK);
        } catch (PerformanceReviewException e) {
            return new ResponseEntity<>("Employee Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("employee/{empId}/goals")
    public ResponseEntity<?> addGoal(@RequestBody Goal g, @PathVariable("empId") int employeeID) throws PerformanceReviewException {
        try {
            Goal goal = performanceReviewService.addGoal(g, employeeID);
            return new ResponseEntity<>(goal, HttpStatus.OK);
        } catch (PerformanceReviewException e) {
            return new ResponseEntity<>("Employee Not Found", HttpStatus.NOT_FOUND);
        }

    }

}

