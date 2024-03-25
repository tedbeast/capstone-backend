package org.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

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
    private boolean acceptRejectFlag;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "siteUsers")
    private SiteUser siteUsers;
}
