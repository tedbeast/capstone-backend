package org.capstone.repository;

import org.capstone.entity.Leave;
import org.capstone.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminReportRepository extends JpaRepository<PerformanceReview, Integer> {
    @Query("SELECT pr.goalType, AVG(pr.rating) FROM PerformanceReview pr GROUP BY pr.goalType")
    List<Object[]> findAverageRatingPerGoalType();

}


