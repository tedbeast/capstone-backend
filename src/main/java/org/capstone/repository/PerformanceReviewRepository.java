package org.capstone.repository;

import org.capstone.entity.Employee;
import org.capstone.entity.PerformanceReview;
//import org.capstone.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {


//    @Query("from PerformanceReview where managerID = :managerID")
//    List<PerformanceReview> findReviewsByManagerID(@Param("managerID")int managerID);
    List<Employee> findEmployeeByManagerID(Integer managerID);

//    @Query("from PerformanceReview where managerID = :managerID")
//    List<SiteUser> findEmployeesByManagerID(@Param("managerID")int managerID);

    public List<PerformanceReview> findPerformanceReviewByManagerID(Integer managerID);

    @Query("from PerformanceReview where employee.employeeID = ?1")
    List<PerformanceReview> findPerformanceReviewByEmployeeID(Integer employeeID);

//    @Query("FROM PerformanceReview WHERE managerID = :managerID")
//    List<PerformanceReview> findPerformanceByManagerID(@Param("managerID")int managerID);

}
