package org.capstone.repository;

import org.capstone.entity.Leave;
import org.capstone.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminReportRepository extends JpaRepository<PerformanceReview, Integer> {
    @Query("SELECT g.goalType, AVG(pr.rating), e.employeeID, e.manager.managerID FROM PerformanceReview pr JOIN pr.goals g JOIN pr.employee e GROUP BY g.goalType, e.employeeID, e.manager.managerID")
    List<Object[]> findAverageRatingPerGoalType();
    @Query("SELECT e.employeeID, COUNT(pr) FROM PerformanceReview pr JOIN pr.employee e GROUP BY e.employeeID")
    List<Object[]> countReviewsPerEmployee();

}


