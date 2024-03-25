package org.capstone.entity;

import jakarta.persistence.*;

import java.util.Date;

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long employeeID;
    private String employeeName;
    private String jobTitle;
    private String phoneNumber;
    private String email;

    private Date anniversary;
    private String password;
    private String addressLine1, addressLine2, city, state, zipCode;

    private String birthdate;
    private int managerID;

    private int performanceID;
    private int leaveID;

    private Roles role;

    @ManyToOne
    @JoinColumn(name = "manager_id")
   @JoinColumn(name = "performance_id")
  @JoinColumn(name = "leave_id")

    private Employee Manager;
    private Employee Performance;

    private Employee Leave;

}
