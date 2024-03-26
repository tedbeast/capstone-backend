package org.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

enum Roles {
    EMPLOYEE,
    MANAGER,
    ADMIN
}
@Entity
@Data
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeID;
    private String password;
    private String name;
    private String jobTitle;
    private String phoneNumber;
    private String email;
    private String addressLine1, addressLine2, city, state, postalCode;
    private Date birthDate;
    private Date anniversary;
    private int managerID;
    @OneToMany
    @JoinColumn(name = "employeeID_fk")
    private List<PerformanceReview> performanceReview;
    @OneToMany
    @JoinColumn(name = "employeeID_fk")
    private List<Leave> leave;
    private Roles role;
}