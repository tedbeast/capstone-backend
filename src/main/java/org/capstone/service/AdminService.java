package org.capstone.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.capstone.Main;
//import org.capstone.dto.PerformanceStatsDto;
//import org.capstone.dto.PerformanceStatsProjection;
import org.capstone.entity.Employee;
import org.capstone.entity.Manager;
import org.capstone.entity.Roles;
import org.capstone.exception.AdminException;
import org.capstone.repository.EmployeeRepository;
import org.capstone.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @PersistenceContext
    private EntityManager entityManager;

    EmployeeRepository employeeRepository;
    ManagerRepository managerRepository;


    @Autowired
    public AdminService(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;

    }

    public Employee updateEmployee(int employeeID, Employee newEmployee) throws AdminException {

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeID);
        Employee employee = employeeOptional.get();
        Roles oldRole = employee.getRole();
        //Check for employee email/phone via review method
//        String eEmail = newEmployee.getEmail().strip();
//        String ePhoneNumber = newEmployee.getPhoneNumber().strip();
//        if (employeeDuplicateReview(eEmail, ePhoneNumber)) {
//            Main.logger.warn("Employee with duplicate email or phone number already exists");
//            throw new AdminException("Employee with email or phone number already exists, please try again.");
//        }

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
            Main.logger.info("Employee " + employeeID + " updated successfully");
        }

        return employee;
    }

    public Employee createManager(Employee employee) throws AdminException {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new AdminException("Employee name cannot be null or empty.");
        }
        //Check for employee email/phone via review method
        String eEmail = employee.getEmail().strip();
        String ePhoneNumber = employee.getPhoneNumber().strip();
        if (employeeDuplicateReview(eEmail, ePhoneNumber)) {
            Main.logger.warn("Manager with duplicate email or phone number already exists");
            throw new AdminException("Manager with email or phone number already exists, please try again.");
        }
        employee.setManager(null);
        Employee savedEmployee = employeeRepository.save(employee);
        if (employee.getRole() == Roles.MANAGER) {
            Manager manager = new Manager();
            manager.setManagerID(savedEmployee.getEmployeeID());
            managerRepository.save(manager);
        }
        return savedEmployee;
    }


    public Employee saveEmployee(int id, Employee employee) throws Exception {
        Optional<Manager> optional = managerRepository.findById(id);
        Manager manager;
        if (optional.isEmpty()) {
            throw new Exception("no such Manager exists...");
        } else {
            manager = optional.get();
        }
        //Check for employee email/phone via review method
        String eEmail = employee.getEmail().strip();
        String ePhoneNumber = employee.getPhoneNumber().strip();
        if (employeeDuplicateReview(eEmail, ePhoneNumber)) {
            Main.logger.warn("Employee with email or phone number already exists, please try again.");
            throw new AdminException("Employee with email or phone number already exists, please try again.");
        }
        employee.setManager(manager);
        employeeRepository.save(employee);
        manager.getEmployees().add(employee);
        managerRepository.save(manager);
        return employee;

    }

    //Check if manager being added is already in manager list
    public boolean employeeDuplicateReview(String email, String phoneNumber) {
        List<Employee> employees = employeeRepository.findAll();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmail().equalsIgnoreCase(email) || employees.get(i).getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void deleteById(int employeeId) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new Exception("No such employee exists,please check the employee id entered.");
        }
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (employee != null) {
            Manager manager = employee.getManager();
            if (manager != null) {
                manager.getEmployees().remove(employee);
            }
            entityManager.remove(employee);
        }
    }


    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        Main.logger.info("Employee List returned: " + employees);
        return employees;
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

    public Employee updateManagerID(int employeeID, Employee newEmployee) throws AdminException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeID);
        Employee employee = employeeOptional.get();

        employee.setManager(newEmployee.getManager());

        employee = employeeRepository.save(employee);

        return employee;
    }


//    public PerformanceStatsProjection findPerformanceStats() {
//        return performanceReviewRepository.findPerformanceStats();
//    }

}


