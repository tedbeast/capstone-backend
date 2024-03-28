package org.capstone.repository;
//import org.capstone.dto.PerformanceStatsDto;
//import org.capstone.dto.PerformanceStatsProjection;
import org.capstone.entity.PerformanceReview;
import org.capstone.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview,Integer> {

    //@Query("SELECT pr.goalType, AVG(pr.rating) as averagerating FROM PerformanceReview pr GROUP BY pr.goalType")


    @Query(value = "SELECT goalType, AVG(rating) as averagerating FROM PerformanceReview GROUP BY goalType", nativeQuery = true)
    static List<Object[]> findAverageRatingPerGoalType() {
        return null;
    }

    //AVerageRatingPergoalType
//    @Query(value = "SELECT PerformanceReview.goalType,avg(PerformanceReview.rating) as averagerating FROM PerformanceReview group by PerformanceReview.goalType", nativeQuery = True)
//    List<Object[]> findAverageRatingPerGoalType();

    //Count of Reviews Per employee
//    @Query (value ="SELECT PerformanceReview.employee, COUNT(PerformanceReview) PerformanceReview_COUNTS FROM PerformanceReview GROUP BY PerformanceReview.employee", nativeQuery = True)
//    List<Object[]> findReviewCountPerEmployee();

    //AverageRatingPerEmployeefilterEmployees
//    @Query("SELECT e, pr.rating FROM Employee e JOIN e.performanceReview pr WHERE " +
//            "(pr.rating >= :rating OR :rating is null) AND " +
//            "(e.manager.managerID = :managerID OR :managerID is null) AND " +
//            "(e.role = :role OR :role is null)")
//    List<Object[]> AverageRatingPerEmployeefilterEmployees(@Param("rating") Double rating,
//                                                           @Param("managerID") Integer managerID,
//                                                           @Param("role") Roles role);



}



//    @Query("SELECT avg(pr.averageScore) as averageScore, count(pr) as count FROM PerformanceReview pr")
//    //PerformanceStatsProjection getPerformanceScores();
//    //PerformanceStatsDto findPerformanceStats();
//    List<Double> findPerformanceScores() ;
//
//    @Query ("SELECT avg(pr.score) FROM PerformanceReview pr")
//    Double calculateAveragePerformaceScores();
//
//    Long countPerformanceReview(); // Inherits from JPARepository for counting total records.