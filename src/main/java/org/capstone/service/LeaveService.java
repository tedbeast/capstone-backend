package org.capstone.service;

import jakarta.transaction.Transactional;
import org.capstone.Main;
import org.capstone.entity.Leave;
import org.capstone.exception.LeaveException;
import org.capstone.exception.LeaveNotFoundException;
import org.capstone.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class LeaveService {
    LeaveRepository leaveRepository;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository) {
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

    //Get All Leaves by Employee ID
    public List<Leave> getAllLeaveByEmployeeIdAndAcceptFlag(int employeeID, boolean acceptedFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeIDAndAcceptedFlag(employeeID, acceptedFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Acccept/Reject Flag are found: " + employeeID);
        }
        return l;
    }

    public List<Leave> getAllLeavesByEmployeeIdAndActiveFlag(int employeeID, boolean activeFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeEmployeeIDAndActiveFlag(employeeID, activeFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Active Flag are found: " + employeeID);
        }
        return l;
    }

    public Leave updateLeave(int leaveId, Leave updatedLeave) throws LeaveNotFoundException {
        Main.logger.info("Updating Leave with ID: {}, id");
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        if (optionalLeave.isEmpty()) {
            throw new LeaveNotFoundException("Leave Not Found");

        }
            Leave existingLeave = optionalLeave.get();

            existingLeave.setStartDate(updatedLeave.getStartDate());
            existingLeave.setEndDate(updatedLeave.getEndDate());

            return leaveRepository.save(existingLeave);
    }

    // Update the active flag for a leave
    public Leave updateActiveFlag(int leaveId, boolean isActive) throws LeaveException {
        Leave leaveToUpdate = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveException("Leave not found with ID: " + leaveId));

        leaveToUpdate.setActiveFlag(isActive);
        return leaveRepository.save(leaveToUpdate);
    }

    // Update the accepted flag for a leave
    public Leave updateAcceptedFlag(int leaveId, boolean isAccepted) throws LeaveException {
        Leave leaveToUpdate = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveException("Leave not found with ID: " + leaveId));

        leaveToUpdate.setAcceptedFlag(isAccepted);
        return leaveRepository.save(leaveToUpdate);
    }


}



