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
@ToString
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

    @JsonIgnore
    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Manager manager;

    @OneToMany
    @JoinColumn(name = "employeeID")
    private List<PerformanceReview> performanceReview;

    @OneToMany
    @JoinColumn(name = "employeeID")
    private List<Leave> leave;
    private Roles role;

}


