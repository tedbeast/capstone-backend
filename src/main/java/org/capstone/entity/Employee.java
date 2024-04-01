package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

  //  @JsonIgnore
    @ManyToOne
    @JsonIgnoreProperties("employees")
    @JoinColumn(name = "managerID")
    private Manager manager;

    @OneToMany
    @JoinColumn(name = "employeeID")
    private List<PerformanceReview> performanceReview;

    @OneToMany
    @JoinColumn(name = "employeeID")
    private List<Leave> leave;
    private Roles role;

    //TODO: Determine if we need this
    //public String getRole() {
    //    return role.toString();
    //}

    //TODO: Determine if we need this
    public void setRole (String role) {
        this.role = Roles.valueOf(role);
    }

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
                ", performanceReview=" + performanceReview +
                ", leave=" + leave +
                ", role=" + role +
                '}';
    }
}