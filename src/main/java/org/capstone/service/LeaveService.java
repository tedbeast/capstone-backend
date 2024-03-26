package org.capstone.service;

import jakarta.transaction.Transactional;
import org.capstone.Main;
import org.capstone.entity.Leave;
import org.capstone.exception.LeaveException;
import org.capstone.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class LeaveService {
    LeaveRepository leaveRepository;

    @Autowired
    public LeaveService (LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    //Get All Leaves by Employee ID
    public List<Leave> getAllLeaveByEmployeeId(int employeeID) throws LeaveException {
        Main.logger.info("Getting leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeID(employeeID);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId is found: " + employeeID);
        }
        return l;
    }

    //Get All Leaves by Employee ID & Accepted Flag
    public List<Leave> getAllLeaveByEmployeeIdAndAcceptFlag(int employeeID, boolean acceptedFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeIDAndAcceptedFlag(employeeID, acceptedFlag);
        if (l.isEmpty()) {

            throw new LeaveException("No leaves for a given employeeId and Accept/Reject Flag are found: " + employeeID);

        }
        return l;
    }

    //Get All Leaves by Employee ID & Active Flag
    public List<Leave> getAllLeavesByEmployeeIdAndActiveFlag(int employeeID, boolean activeFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeIDAndActiveFlag(employeeID, activeFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Active Flag are found: " + employeeID);
        }
        return l;
    }


   public Leave addLeave(Leave leave) throws LeaveException {
       // Validate leave details
       if (leave.getLeaveName() == null || leave.getLeaveName().isEmpty()) {
           throw new LeaveException("Leave name is required");
       }
       if (leave.getStartDate() == null || leave.getEndDate() == null) {
           throw new LeaveException("Start date and end date are required");
       }
       if (leave.getEmployee() == null) {
           throw new LeaveException("Employee is required");
       }
       if (leave.getManager() == null) {
           throw new LeaveException("Manager is required");
       }
       if (leave.isActiveFlag() && !leave.isAcceptedFlag()) {
          return leave;
       } else {
       throw new LeaveException("Leave cannot be added");
       }
       //leave.setActiveFlag(true);
       //leave.setAcceptedFlag(false);

       // Save the leave record
       //return leaveRepository.save(leave);
   }
}


