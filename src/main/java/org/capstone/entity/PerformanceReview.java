package org.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int performanceReviewID;
    public String goalType;
    public String employeeComments;
    public Date targetDate;
    public int weight;
    public Date deadlineDate;
    public String managerComments;
    private int managerID; //this will likely be an FK to a manager entity created by.......?

    @ManyToOne
    @JoinColumn(name="employeeid")
    private Employee employee; //look up how to reference a column from another entity

}
