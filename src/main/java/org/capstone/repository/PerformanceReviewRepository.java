package org.capstone.repository;

import org.capstone.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {

    public List<PerformanceReview> findPerformanceReviewByManagerID(Integer managerID);

//    @Query("FROM PerformanceReview WHERE managerID = :managerID")
//    List<PerformanceReview> findPerformanceByManagerID(@Param("managerID")int managerID);
}
