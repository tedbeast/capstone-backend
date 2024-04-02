package org.capstone.repository;

import org.capstone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("from Employee where manager.managerID = :managerID")
    List<Employee> findEmployeeByManagerId(@Param("managerID") Integer managerID);
}