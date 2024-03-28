package org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.capstone.service.PerformanceReviewService;

import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int goalID;
    public String goalType;
    public String goalDescription;
    public String employeeComments;
    public int weight;

    @ManyToOne
    @JoinColumn(name="performanceReviewID")
    @JsonIgnoreProperties("goals")
    public PerformanceReview performanceReview;

}
