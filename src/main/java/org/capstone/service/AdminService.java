package org.capstone.service;

import org.capstone.Main;
//import org.capstone.dto.PerformanceStatsDto;
//import org.capstone.dto.PerformanceStatsProjection;
import org.capstone.entity.Employee;
import org.capstone.entity.Manager;
import org.capstone.entity.Roles;
import org.capstone.exception.AdminException;
import org.capstone.repository.AdminReportRepository;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.ManagerRepository;
import org.capstone.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    EmployeeRepository employeeRepository;
    ManagerRepository managerRepository;




    @Autowired
    public AdminService(EmployeeRepository employeeRepository, ManagerRepository managerRepository ) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;


    }

    public Employee updateEmployee(int employeeID, Employee newEmployee) throws AdminException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeID);
        Employee employee = employeeOptional.get();
        Roles oldRole = employee.getRole();

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
        employee.setRole(newEmployee.getRole());

        if (employee.getName().trim().isEmpty()) {
            throw new AdminException("Name is empty, must have a name");
        } else {
            if ((oldRole == Roles.EMPLOYEE || oldRole == Roles.ADMIN) && newEmployee.getRole() == Roles.MANAGER) {
                Manager manager = new Manager();
                manager.setEmployees(new ArrayList<>());
                manager.getEmployees().add(employee);
                managerRepository.save(manager);
                employee.setManager(manager);
            }
            employee = employeeRepository.save(employee);
        }

        return employee;
    }

    public Employee createManager(Employee employee) throws AdminException {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new AdminException("Employee name cannot be null or empty.");
        }

        Employee savedEmployee = employeeRepository.save(employee);

        if (employee.getRole() == Roles.MANAGER){
            Manager manager = new Manager();

            manager.setManagerID(savedEmployee.getEmployeeID());

            managerRepository.save(manager);
        }
        return savedEmployee;
    }

    public Employee saveEmployee(int id, Employee employee) throws Exception {
        Optional<Manager> optional = managerRepository.findById(id);
        Manager manager;
        if(optional.isEmpty()){
            throw new Exception("no such Employee...");
        }else{
            manager = optional.get();
        }
        Employee savedEmployee = employeeRepository.save(employee);
        manager.getEmployees().add(savedEmployee);
        managerRepository.save(manager);
        return savedEmployee;
    }

    public Employee deleteById(int employeeId) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new Exception("No such employee exists,please check the employee id entered.");
        }
        employeeRepository.deleteById(employeeId);//Remove the employee from the list
        return employeeOptional.get();//Return deleted employee?
    }



    public List<Employee> getAllEmployees() {
        Main.logger.info("Employee List returned: ");
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int employeeId) throws AdminException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Main.logger.info("Employee found: " + employee);
            return employeeOptional.get();
        } else {
            Main.logger.warn("Employee not found" + employeeId);
            throw new AdminException("Employee is not found with that id" + employeeId);
        }
    }

    public List<Manager> getAllManagers() {
        Main.logger.info("Manager List returned:");
        return managerRepository.findAll();
    }





}


