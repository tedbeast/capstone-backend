package org.capstone.repository;

import java.util.Optional;
import org.capstone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
        Employee findByEmployeeID(int employeeID);

    @Query("SELECT e FROM Employee e JOIN FETCH e.manager WHERE e.id = :id")
    Optional<Employee> findByIdWithManager(@Param("id") int id);

}