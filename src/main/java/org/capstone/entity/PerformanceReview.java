package org.capstone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PerformanceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int performanceId;
    //new added fields
    private double averageScore;
   // private long reviewCount;
}
