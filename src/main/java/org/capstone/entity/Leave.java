package org.capstone.entity;

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
    private int leaveId;
    private String leaveName;
    private Timestamp startDate;
    private Timestamp endDate;
    //private LeaveStatus leaveStatus;
    private boolean acceptedFlag;
    private boolean activeFlag;
    @ManyToOne
    @JoinColumn(name = "employeeID")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "managerID")
    private Manager manager;

}