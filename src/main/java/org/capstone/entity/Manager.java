
package org.capstone.entity;

import jakarta.persistence.*;

import java.io.Console;
import java.util.List;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerID;

    private void mergeConflictTest(){
        System.out.println("Test");
    }
    @OneToMany
    private List<Employee> employees;
}


