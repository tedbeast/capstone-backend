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
    public List<Leave> getAllLeaveByEmployeeId(int employeeId) throws LeaveException {
        Main.logger.info("Getting leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeId(employeeId);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId is found: " + employeeId);
        }
        return l;
    }

    //Get All Leaves by Employee ID
    public List<Leave> getAllLeaveByEmployeeIdAndAcceptFlag(int employeeId, boolean acceptedFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeIdAndAcceptedFlag(employeeId, acceptedFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Acccept/Reject Flag are found: " + employeeId);
        }
        return l;
    }

    public List<Leave> getAllLeavesByEmployeeIdAndActiveFlag(int employeeId, boolean activeFlag) throws LeaveException {
        Main.logger.info("Getting accepted leaves by employee");
        List <Leave> l = leaveRepository.findByEmployeeIdAndActiveFlag(employeeId, activeFlag);
        if (l.isEmpty()) {
            throw new LeaveException("No leaves for a given employeeId and Active Flag are found: " + employeeId);
        }
        return l;
    }
}


