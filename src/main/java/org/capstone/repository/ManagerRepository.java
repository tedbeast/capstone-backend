package org.capstone.repository;

import org.capstone.entity.Employee;
import org.capstone.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    @Query("select employees from Manager where managerID = :managerID")
    List<Employee> findEmployeesByManagerId(@Param("managerID") Integer managerID);
}
