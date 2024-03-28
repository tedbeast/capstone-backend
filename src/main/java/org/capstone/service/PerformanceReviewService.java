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

import java.time.*;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;


@Service
public class PerformanceReviewService {
    PerformanceReviewRepository performanceReviewRepository;
    GoalRepository goalRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository, GoalRepository goalRepository, EmployeeRepository employeeRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.goalRepository = goalRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<PerformanceReview> getAllPerformanceReview() {
        Main.logger.info("Performance Review Get: Attempting to get all performance reviews.");
        return performanceReviewRepository.findAll();
    }

    // to do: use employee repository
    public List<Employee> getAllEmployeeByManagerID(int managerID){
        return null; //performanceReviewRepository.findEmployeeByManagerID(managerID);
    }

    public Employee checkIfEmployeeExistsByID(int employeeID) throws PerformanceReviewException {
        Optional<Employee> optional = employeeRepository.findById(employeeID);
        if (optional.isEmpty()) {
            Main.logger.warn("Performance Review Get: Employee Not Found.");
            throw new PerformanceReviewException("Employee Not Found.");
        }
        return optional.get();
    }

    public List<PerformanceReview> getAllPerformanceReviewsByEmployeeID(int employeeID) throws PerformanceReviewException {
        Main.logger.info("Performance Review GET: Attempting to get all performance reviews by Employee ID.");
        checkIfEmployeeExistsByID(employeeID);
        return performanceReviewRepository.findPerformanceReviewByEmployeeID(employeeID);
    }

    public List<Goal> getAllGoalsByEmployeeID(int employeeID) throws PerformanceReviewException {
        Main.logger.info("Goal GET: Attempting to get all goals by Employee ID.");
        checkIfEmployeeExistsByID(employeeID);
        return goalRepository.findGoalByEmployeeID(employeeID);
    }

    public List<PerformanceReview> getAllPerformanceByManager(int id){
        Main.logger.info("logging method execution: PerformanceReviewService.getAllPerformanceByManager");
        Main.logger.info("PerformanceReviewService.getAllPerformanceByManager: Performance Review list successfully retrieved.");
        return performanceReviewRepository.findPerformanceReviewByManagerID(id);
    }

    public Goal employeeAddComments(int employeeID, int performanceReviewID, Goal g, int goalID){
        Optional<Goal> optional = goalRepository.findById(goalID);
        if(optional.isEmpty()){
            //dothiserrorthing
        }
        Goal goal = optional.get();
        goal.setEmployeeComments(g.getEmployeeComments());
        goalRepository.save(goal);
        return goal;
        /*
        Optional<PerformanceReview> optional = performanceReviewRepository.findById(performanceReviewID);

        PerformanceReview performanceReview = optional.get();
        performanceReview
        performanceReview.setEmployeeComments(p.employeeComments);
        performanceReviewRepository.save(performanceReview);
        return performanceReview;
        */
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

    public Date getLatestPerformanceReview(List<PerformanceReview> performanceReviewList) {
        Date maxDate = performanceReviewList.get(0).getDeadlineDate();
        for (int i = 1; i < performanceReviewList.size(); i++){
            if (performanceReviewList.get(i).getDeadlineDate().after(maxDate)) {
                maxDate = performanceReviewList.get(i).getDeadlineDate();
            }
        }
        return maxDate;
    }

    public PerformanceReview addPerformanceReview (int employeeID) throws PerformanceReviewException {
        Main.logger.info("Performance Review POST: Attempting to create a new Performance Review for an employee.");
        Employee employee = checkIfEmployeeExistsByID(employeeID);
        PerformanceReview performanceReview = new PerformanceReview();
        if (employee != null) {
            List<PerformanceReview> performanceReviewList = getAllPerformanceReviewsByEmployeeID(employeeID);
            if (performanceReviewList.isEmpty()) {
                performanceReview.setDeadlineDate(java.sql.Date.valueOf(LocalDate.now().with(lastDayOfYear())));
            }
            else {
                Date d = getLatestPerformanceReview(performanceReviewList);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.YEAR, 1);
                performanceReview.setDeadlineDate(c.getTime());
            }
            performanceReview.setEmployee(employee);

            performanceReviewList.add(performanceReview);
            employee.setPerformanceReview(performanceReviewList);
        }
        return performanceReviewRepository.save(performanceReview);
    }

    public Goal addGoal(Goal goal, int employeeID) throws PerformanceReviewException {
        Main.logger.info("Goal POST: Attempting to create a new goal for an employee.");
        Employee employee = checkIfEmployeeExistsByID(employeeID);
        if (employee != null) {
            List<PerformanceReview> performanceReviewList = performanceReviewRepository.findPerformanceReviewByEmployeeID(employeeID);
            if (performanceReviewList.isEmpty()) {
                PerformanceReview newPerformanceReview = new PerformanceReview();
                newPerformanceReview.setEmployee(employee);
                newPerformanceReview.setDeadlineDate(java.sql.Date.valueOf(LocalDate.now().with(lastDayOfYear())));
                performanceReviewRepository.save(newPerformanceReview);
                performanceReviewList.add(newPerformanceReview);
                employee.getPerformanceReview().add(newPerformanceReview);
            }
            List<Goal> goalList = goalRepository.findGoalByEmployeeID(employeeID);
            PerformanceReview performanceReview = performanceReviewRepository.findPerformanceReviewByEmployeeIDandYear(employeeID, java.sql.Date.valueOf(LocalDate.now().with(lastDayOfYear())));
            goalList.add(goal);
            performanceReviewList.get(0).setGoals(goalList);
            goal.setPerformanceReview(performanceReview);
            goal.setPerformanceReview(performanceReviewList.get(0));
        }
        return goalRepository.save(goal);
    }


}
