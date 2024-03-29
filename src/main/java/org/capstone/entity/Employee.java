package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode

public class Employee {
    @Getter
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

//    @JsonIgnore
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "manager_id")
    //@JsonIgnoreProperties("employees")
    private Manager manager;

    @OneToMany
    @JoinColumn(name = "employeeID")
    private List<PerformanceReview> performanceReview;

    @OneToMany
    @JoinColumn(name = "employeeID")
    private List<Leave> leave;
    private Roles role;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", birthDate=" + birthDate +
                ", anniversary=" + anniversary +
//                ", performanceReview=" + performanceReview +
//  Resolved the leave 500 issue caused by infinite loop
//  ", leave=" + leave +
                ", role=" + role +
                '}';
    }
}


