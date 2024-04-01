package org.capstone.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

//enum LeaveStatus {
//    APPROVED,
//    REJECTED,
//    PENDING
//}
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String leaveName;
    private Timestamp startDate;
    private Timestamp endDate;
    //private LeaveStatus leaveStatus;
    private boolean acceptedFlag;
    private boolean activeFlag;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employeeID")
    @JsonIgnoreProperties("leave")
    private Employee employee;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "managerID")
//    @JsonIgnoreProperties("employess")
//    private Manager manager;

}