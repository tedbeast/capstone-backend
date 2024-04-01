package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    public String goalDescription;
    public String employeeComments;
    public Date targetDate;
    public int weight;
    public Date deadlineDate;
    public String managerComments;
    public double rating;

    @ManyToOne
    @JoinColumn(name="employeeid")
    @JsonIgnoreProperties("performanceReview")
    private Employee employee; //look up how to reference a column from another entity
}
