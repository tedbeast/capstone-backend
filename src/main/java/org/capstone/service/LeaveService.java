package org.capstone.service;

import jakarta.transaction.Transactional;
import org.capstone.Main;
import org.capstone.entity.Leave;
import org.capstone.entity.Manager;
import org.capstone.exception.LeaveException;
import org.capstone.exception.LeaveManagerNotFoundException;
import org.capstone.exception.LeaveNotFoundException;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.LeaveRepository;
import org.capstone.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class LeaveService {
    LeaveRepository leaveRepository;
    EmployeeRepository employeeRepository;
    ManagerRepository managerRepository;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }

    //Get All Leaves
    public List<Leave> getAllLeaves() throws LeaveException {
        Main.logger.info("Getting leaves by employee");
        List <Leave> l = leaveRepository.findAll();
        if (l.isEmpty()) {
            throw new LeaveException("No leaves found");
        }
        return l;
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

    public List<Leave> getAllEmployeeLeavesForManager(int managerID) throws LeaveManagerNotFoundException {
        Main.logger.info("Getting all employee leaves for manager ID " + managerID);
        // Get the employees who report to this manager
        Optional<Manager> optionalManager = managerRepository.findById(managerID);
        List<Leave> l = new ArrayList<Leave>();
        if (optionalManager.isPresent()) {
            l = leaveRepository.findAllEmployeeLeaveByManager(managerID);
            if (l.isEmpty()) {
                Main.logger.info("The manager ID " + managerID + " does not have any leave requests for supervised employees.");
            }
        } else {
            Main.logger.warn("The manager ID, " + managerID + " does not have a Manager record");
            throw new LeaveManagerNotFoundException("Manager ID supplied does not have any Manager record");
        }
        return l;
    }

    public List<Leave> getEmployeeLeavesForManagerByStatus(int managerID, boolean activeFlag, boolean acceptedFlag) throws LeaveManagerNotFoundException {
        Main.logger.info("Getting all employee leaves for a manager");
        // Get the employees who report to this manager
        Optional<Manager> optionalManager = managerRepository.findById(managerID);
        List<Leave> l = new ArrayList<Leave>();
        if (optionalManager.isPresent()) {
            l = leaveRepository.findEmployeeLeaveByManagerByStatusFlags(managerID, activeFlag, acceptedFlag);
            if (l.isEmpty()) {
                Main.logger.info("The manager ID " + managerID + " does not have any leave requests for employees for the requested status. accepted_flag: " + acceptedFlag + ", active_flag: " + activeFlag);
//                throw new LeaveException("The manager does not have any leave requests for supervised employees.");
            }
        } else {
            Main.logger.warn("Employee ID " + managerID + " does not have a Manager record");
            throw new LeaveManagerNotFoundException("Employee ID supplied does not have a Manager record");
        }
        return l;
    }

    public Leave updateLeave(int Id, Leave updatedLeave) throws LeaveNotFoundException {
        Main.logger.info("Updating Leave with ID: {}, id");
        Optional<Leave> optionalLeave = leaveRepository.findById(Id);
        if (optionalLeave.isEmpty()) {
            throw new LeaveNotFoundException("Leave Not Found");

        }
            Leave existingLeave = optionalLeave.get();

            existingLeave.setStartDate(updatedLeave.getStartDate());
            existingLeave.setEndDate(updatedLeave.getEndDate());

            return leaveRepository.save(existingLeave);
    }



    public Leave updateLeaveById(int Id, Leave updatedLeave) throws LeaveNotFoundException {
        Main.logger.info("Updating Leave with ID: {}, id");
        Optional<Leave> optionalLeave = leaveRepository.findById(Id);
        if (optionalLeave.isEmpty()) {
            throw new LeaveNotFoundException("Leave Not Found");

        }
        Leave existingLeave = optionalLeave.get();

        existingLeave.setStartDate(updatedLeave.getStartDate());
        existingLeave.setEndDate(updatedLeave.getEndDate());
        existingLeave.setActiveFlag(true);
        existingLeave.setAcceptedFlag(false);

        return leaveRepository.save(existingLeave);
    }

    // Update the active flag for a leave
    public Leave updateActiveFlag(int Id, boolean isActive) throws LeaveException {
        Leave leaveToUpdate = leaveRepository.findById(Id)
                .orElseThrow(() -> new LeaveException("Leave not found with ID: " + Id));

        leaveToUpdate.setActiveFlag(isActive);
        return leaveRepository.save(leaveToUpdate);
    }

    // Update the accepted flag for a leave
    public Leave updateAcceptedFlag(int Id, boolean isAccepted) throws LeaveException {
        Leave leaveToUpdate = leaveRepository.findById(Id)
                .orElseThrow(() -> new LeaveException("Leave not found with ID: " + Id));

        leaveToUpdate.setAcceptedFlag(isAccepted);
        return leaveRepository.save(leaveToUpdate);
    }



    public Leave deleteLeave(int leaveId) throws LeaveNotFoundException {
        Main.logger.info("Deleting leave");
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        Leave leaveToDelete;
        if(optionalLeave.isEmpty()){
            throw new LeaveNotFoundException("Leave with id " + leaveId + " not found");
        }else{
            leaveToDelete = optionalLeave.get();
        }
        leaveRepository.delete(leaveToDelete);
        return leaveToDelete;
    }


   public Leave addLeave(Leave leave) throws LeaveException {
       Main.logger.info("Attempting to add a new leave: " + leave);
       //check for duplicate leaves
       List<Leave> existingLeaves = leaveRepository.findByLeaveNameAndStartDateAndEndDate(leave.getLeaveName()
               ,leave.getStartDate(), leave.getEndDate());
       if (!existingLeaves.isEmpty()) {
           throw new LeaveException("Leave with same detail already Exists");
       }

       // Validate leave details
       if (leave.getLeaveName() == null || leave.getLeaveName().isEmpty()) {
           throw new LeaveException("Leave name is required");
       }
       if (leave.getStartDate() == null || leave.getEndDate() == null) {
           throw new LeaveException("Start date and end date are required");
       }


           //if (leave.isActiveFlag() && !leave.isAcceptedFlag()) {

           leave.setAcceptedFlag(false);            // new leave is set to Not Approved
           leave.setActiveFlag(true);               // new leave is set to Active

           leaveRepository.save(leave);
           Main.logger.info("New Leave added: " + leave);
          return leaveRepository.save(leave);


   }
    public List<Leave> getAllLeavesByActiveStatus(boolean activeStatus) throws LeaveException {
        Main.logger.info("Getting leaves by active status");
        List<Leave> leaves = leaveRepository.findByActiveFlag(activeStatus);
        if (leaves.isEmpty()) {
            throw new LeaveException("No leaves with active status " + activeStatus + " are found");
        }
        return leaves;
    }


}



