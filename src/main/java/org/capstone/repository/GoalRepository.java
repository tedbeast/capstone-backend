package org.capstone.repository;

import org.capstone.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    @Query("from Goal where performanceReview.employee.employeeID = ?1")
    List<Goal> findGoalByEmployeeID(Integer employeeID);
}
