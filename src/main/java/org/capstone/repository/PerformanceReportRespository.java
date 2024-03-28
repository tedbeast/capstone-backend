package org.capstone.repository;

import org.capstone.entity.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerformanceReportRespository {

    //AVerageRatingPergoalType
    @Query("SELECT PerformanceReview.goalType,avg(PerformanceReview.rating) as averagerating FROM PerformanceReview group by PerformanceReview.goalType")
    List<Object[]> findAverageRatingPerGoalType();

    //Count of Reviews Per employee
    @Query ("SELECT PerformanceReview.employee, COUNT(PerformanceReview) PerformanceReview_COUNTS FROM PerformanceReview GROUP BY PerformanceReview.employee")
    List<Object[]> findReviewCountPerEmployee();

    //AverageRatingPerEmployeefilterEmployees
    @Query("SELECT e, pr.rating FROM Employee e JOIN e.performanceReview pr WHERE " +
            "(pr.rating >= :rating OR :rating is null) AND " +
            "(e.manager.managerID = :managerID OR :managerID is null) AND " +
            "(e.role = :role OR :role is null)")
    List<Object[]> AverageRatingPerEmployeefilterEmployees(@Param("rating") Double rating,
                                                           @Param("managerID") Integer managerID,
                                                           @Param("role") Roles role);
}
