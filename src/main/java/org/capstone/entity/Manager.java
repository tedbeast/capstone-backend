package org.capstone.entity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Console;
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

    @OneToOne
    private Employee managerEmployee;

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