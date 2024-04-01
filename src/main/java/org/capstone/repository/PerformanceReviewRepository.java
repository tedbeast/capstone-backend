package org.capstone.repository;

import org.capstone.entity.Employee;
import org.capstone.entity.PerformanceReview;
//import org.capstone.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {

    //List<Employee> findEmployeeByManagerID(Integer managerID);

    @Query("from PerformanceReview where employee.manager.managerID = ?1")
    List<PerformanceReview> findPerformanceReviewByManagerID(Integer managerID);

    @Query("from PerformanceReview where employee.employeeID = ?1")
    List<PerformanceReview> findPerformanceReviewByEmployeeID(Integer employeeID);

    @Query("from PerformanceReview where employee.employeeID = ?1 and deadlineDate >= ?2 order by deadlineDate desc limit 1")
    PerformanceReview findPerformanceReviewByEmployeeIDandYear(Integer employeeID, Date deadlineDate);

}
