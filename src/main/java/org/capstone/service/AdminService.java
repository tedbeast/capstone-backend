package org.capstone.service;

import org.capstone.Main;
import org.capstone.entity.Employee;
import org.capstone.exception.AdminException;
import org.capstone.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    EmployeeRepository employeeRepository;

    @Autowired
    public AdminService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Update Site user by EmployeeID
    public Employee updateEmployee(int employeeID, Employee newEmployee) throws AdminException {
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
        employee.setRole(newEmployee.getRole());


        if (employee.getName().trim().isEmpty()) {
            throw new AdminException("Name is Empty must have a name");
        } else {
            employeeRepository.save(employee);

            return employee;
        }
    }

    public Employee createEmployee(Employee employee) throws AdminException {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new AdminException("Employee name cannot be null or empty.");
        }
        return employeeRepository.save(employee);
    }

    public Employee deleteById(int employeeId) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new Exception("No such employee exists,please check the employee id entered.");
        }
        employeeRepository.deleteById(employeeId);//Remove the employee from the list
        return employeeOptional.get();//Return deleted employee?


        //Delete Site user by EmployeeID

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
}


