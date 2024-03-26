package org.capstone.repository;

import org.capstone.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "LeaveRepository")
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    List<Leave> findByEmployeeId(int employeeId);

    List<Leave> findByEmployeeIdAndAcceptedFlag(int employeeId, boolean acceptedFlag);

    List<Leave> findByEmployeeIdAndActiveFlag(int employeeId, boolean activeFlag);

}

