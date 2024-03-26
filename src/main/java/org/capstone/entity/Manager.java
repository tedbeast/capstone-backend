<<<<<<< HEAD
package org.capstone.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerID;
    @OneToMany
    private List<Employee> employees;
}
=======
package org.capstone.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerID;
    @OneToMany
    private List<Employee> employees;
}
>>>>>>> origin/login-team
