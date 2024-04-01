package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data
@EqualsAndHashCode

public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerID;

    @JsonIgnore
    @OneToOne
    private Employee managerEmployee;

    @OneToMany
    @JoinColumn(name="employee_fk")
    private List<Employee> employees;

    @JsonProperty("employees")
    public ArrayList<Integer> getEmployeeID() {
        ArrayList<Integer> employeesId = new ArrayList<>();
        for (Employee employee : employees) {
            employeesId.add(employee.getEmployeeID());
        }
        return employeesId;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerID=" + managerID +
                '}';
    }
}
