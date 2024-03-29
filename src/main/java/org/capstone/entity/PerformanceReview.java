package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    public Date deadlineDate;
    public String managerComments;
    public double rating;

    @OneToMany
    public List<Goal> goals;

    @ManyToOne
    @JoinColumn(name="employeeid")
    @JsonIgnoreProperties("performanceReview")
    public Employee employee; //look up how to reference a column from another entity
}
