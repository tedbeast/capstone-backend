package org.capstone.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    //@JsonIgnore // Prevents the serialization of the whole Employee object
    @ManyToOne
    @JoinColumn(name = "employeeID")
    @JsonIgnoreProperties("leave")
    @JsonBackReference //added
    private Employee employee;

    // Add a method to serialize only employeeID
    @JsonProperty("employeeID")
    public int getEmployeeID() {
        return this.employee != null ? this.employee.getEmployeeID() : 0;
    }

    @JsonProperty("employeeName")
    public String getEmployeeName() {
        return this.employee != null ? this.employee.getName() : null;
    }

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "managerID")
//    @JsonIgnoreProperties("employess")
//    private Manager manager;

}