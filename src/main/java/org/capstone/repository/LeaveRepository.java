package org.capstone.repository;

import org.capstone.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository(value = "LeaveRepository")
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    List<Leave> findByEmployeeEmployeeID(int employeeID);

    List<Leave> findByEmployeeEmployeeIDAndAcceptedFlag(int employeeID, boolean acceptedFlag);

    List<Leave> findByEmployeeEmployeeIDAndActiveFlag(int employeeID, boolean activeFlag);

    
    List<Leave> findByActiveFlag(boolean activeFlag);

    List<Leave> findByLeaveNameAndStartDateAndEndDate(String leaveName, Timestamp startDate, Timestamp endDate);

    @Query("SELECT l FROM Manager m, Leave l WHERE m.managerid = :contextManagerID and m.employeeid = l.employeeid")
    List<Leave> findAllEmployeeLeaveByManager(@Param("contextManagerID") int contextManagerID);


}
