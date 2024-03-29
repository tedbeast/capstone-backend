package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @ManyToOne
    @JoinColumn(name="employeeid")
    @JsonIgnoreProperties("performanceReview")
    public Employee employee;
    @OneToMany
    @JsonManagedReference
    public List<Goal> goals;
}
