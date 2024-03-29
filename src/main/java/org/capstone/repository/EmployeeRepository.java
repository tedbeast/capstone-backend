package org.capstone.repository;

import org.capstone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e.employeeID, AVG(p.rating) FROM Employee e JOIN e.performanceReview p GROUP BY e.employeeID")
    List<Object[]> findAverageRatingPerEmployee();

    @Query("SELECT e.employeeID, e.name, p.rating, COUNT(e) FROM Employee e JOIN e.performanceReview p WHERE p.rating < 3 GROUP BY e.employeeID, e.name")
    List<Object[]> countEmployeesWithRatingUnderThree();





}
