package org.sqlTool.model;
/**
 * @COPYRIGHT (C) 2021 Renewal 365
 *
 * <p>All rights reserved
 */

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/** Created by Edward on 7/26/2021. */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {

  @Id
  private long employeeId;

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private LocalDate hireDate;
  private Float salary;
  private Float commissionPct;

  @ManyToOne
  @JoinColumn(name = "job_id")
  private Job job;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @OneToMany(fetch=FetchType.EAGER, mappedBy = "employee", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<JobHistory> jobHistoryList;
}
