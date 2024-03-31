package org.capstone.repository;

import org.capstone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e.employeeID, e.name, e.jobTitle, m.managerID, p.deadlineDate, AVG(p.rating) " +
            "FROM Employee e " +
            "JOIN e.performanceReview p " +
            "JOIN e.manager m " +
            "GROUP BY e.employeeID")
    List<Object[]> findAverageRatingPerEmployee();

    @Query("SELECT e.employeeID, e.name, e.jobTitle, e.manager.managerID,  p.rating, COUNT(e) " +
            "FROM Employee e " +
            "JOIN e.performanceReview p " +
            "WHERE p.rating < 3 " +
            "GROUP BY e.employeeID, e.name")
    List<Object[]> countEmployeesWithRatingUnderThree();

    @Query("from Employee where manager.managerID = ?1")
    List<Employee> findEmployeeByManagerId(Integer managerID);

    @Query("SELECT e.employeeID, e.name AS employeeName, e.manager.managerID, m.name AS managerName " +
            "FROM Employee e " +
            "JOIN Employee m ON e.manager.managerID = m.employeeID")
    List<Object[]> findEmployeeAndManagerNames();

}
