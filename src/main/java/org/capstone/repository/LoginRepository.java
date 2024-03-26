package org.capstone.repository;

import org.capstone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmployeeID(int employeeID);
}
