package org.capstone.service;

import org.capstone.repository.AdminReportRepository;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReportService {


    AdminReportRepository adminReportRepository;


    @Autowired
    public AdminReportService(AdminReportRepository adminReportRepository) {
              this.adminReportRepository =adminReportRepository;
    }

    public List<Object[]> findAverageRatingPerGoalType() {
        return adminReportRepository.findAverageRatingPerGoalType();
    }
}
