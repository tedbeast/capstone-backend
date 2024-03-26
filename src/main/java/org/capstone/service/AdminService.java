package org.capstone.service;

import org.capstone.entity.Employee;

import org.capstone.exception.AdminException;
import org.capstone.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminService {

    EmployeeRepository employeeRepository;

    @Autowired
    public AdminService(EmployeeRepository adminRepository){
        this. employeeRepository = employeeRepository;
    }

    //Update Site user by EmployeeID
    public Employee updateSiteUser(int employeeID, Employee newEmployee) throws AdminException {
        Optional<Employee> employeeeOptional = employeeRepository.findById(employeeID);
        Employee employee = employeeeOptional.get();

        employee.setName(newEmployee.getName());
        employee.setPassword(newEmployee.getPassword());
        employee.setJobTitle(newEmployee.getJobTitle());
        employee.setPhoneNumber(newEmployee.getPhoneNumber());
        employee.setEmail(newEmployee.getEmail());
        employee.setAddressLine1(newEmployee.getAddressLine1());
        employee.setAddressLine2(newEmployee.getAddressLine2());
        employee.setCity(newEmployee.getCity());
        employee.setState(newEmployee.getState());
        employee.setPostalCode(newEmployee.getPostalCode());
        employee.setBirthDate(newEmployee.getBirthDate());
        employee.setAnniversary(newEmployee.getAnniversary());


        if (employee.getName().trim().isEmpty()) {
            throw new AdminException ("Name is Empty must have a name");
        }
        else {
            employeeRepository.save(employee);

            return employee;
        }
    }

    //Delete Site user by EmployeeID
}
