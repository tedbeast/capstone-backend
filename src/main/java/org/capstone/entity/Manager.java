package org.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@EqualsAndHashCode

public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerID;
    @OneToMany
    @JoinColumn(name="employee_fk")
    private List<Employee> employees;


}


