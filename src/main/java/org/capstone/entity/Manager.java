package org.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data
@EqualsAndHashCode

public class Manager {
    @Id
    private int managerID;
//    Merge commented out code into leaves branch, but not into master.  This is pending admin team update
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Merge commented out code into leaves branch, but not into master.  This is pending admin team update



//    private Employee managerEmployee;

    @OneToMany
    @JoinColumn(name="employee_fk")
    private List<Employee> employees;

    @Override
    public String toString() {
        return "Manager{" +
                "managerID=" + managerID +
                '}';
    }
}
