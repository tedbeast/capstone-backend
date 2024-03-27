package org.capstone.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerID;

    @OneToOne
    private Employee managerEmployee;
    @OneToMany
    private List<Employee> employees;
}

