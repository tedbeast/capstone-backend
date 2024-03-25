package org.capstone.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

enum LeaveStatus {
    APPROVED,
    REJECTED,
    PENDING
}
@Entity
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int leaveId;
    private String leaveName;
    private Timestamp startDate;
    private Timestamp endDate;
    //private LeaveStatus leaveStatus;
    private boolean acceptRejectFlag;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "employee_fk")
    private List<SiteUser> siteUsers;
}
