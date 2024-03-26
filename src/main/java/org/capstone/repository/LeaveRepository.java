package org.capstone.repository;

import org.capstone.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "LeaveRepository")
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    List<Leave> findByEmployeeEmployeeID(int employeeID);

    List<Leave> findByEmployeeEmployeeIDAndAcceptedFlag(int employeeID, boolean acceptedFlag);

    List<Leave> findByEmployeeEmployeeIDAndActiveFlag(int employeeID, boolean activeFlag);

}
