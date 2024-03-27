
package org.capstone.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerD;
    private int merge;
    @OneToMany
    private List<Employee> employees;
}


