package org.capstone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.capstone.entity.PerformanceReview;

import java.util.Date;

enum Roles {
    EMPLOYEE,
    MANAGER,
    ADMIN
}
@Entity
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
    private PerformanceReview performanceReview;
    private Leave leave;
    private Roles role;
}
