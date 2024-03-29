package org.capstone.service;

import org.capstone.repository.AdminReportRepository;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminReportService {


    AdminReportRepository adminReportRepository;
    EmployeeRepository employeeRepository;


    @Autowired
    public AdminReportService(AdminReportRepository adminReportRepository, EmployeeRepository employeeRepository) {
              this.adminReportRepository =adminReportRepository;
              this.employeeRepository = employeeRepository;
    }

    public List<Object[]> findAverageRatingPerGoalType() {
        return adminReportRepository.findAverageRatingPerGoalType();
    }

    public List<Object[]> getAverageRatingPerEmployee() {
        return employeeRepository.findAverageRatingPerEmployee();
    }

    public List<Object[]> getNumberOfEmployeesWithRatingUnderThree() {
        return employeeRepository.countEmployeesWithRatingUnderThree();
    }


    public Map<Integer, Long> getCountOfReviewsPerEmployee() {
        List<Object[]> reviewsCount = adminReportRepository.countReviewsPerEmployee();
        Map<Integer, Long> reviewsCountMap = new HashMap<>();
        for (Object[] review : reviewsCount) {
            Integer employeeId = (Integer) review[0];
            Long count = (Long) review[1];
            if (employeeId != null) {
                reviewsCountMap.put(employeeId, count != null ? count : 0);
            }
        }
        return reviewsCountMap;
    }

}
