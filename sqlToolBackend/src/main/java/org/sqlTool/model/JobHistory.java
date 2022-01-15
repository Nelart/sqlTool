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

/** Created by Edward on 7/26/2021. */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "JOB_HISTORY")
public class JobHistory implements Serializable {

  @Id
  private long historyId;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  private LocalDate startDate;

  @ManyToOne
  @JoinColumn(name = "job_id")
  private Job job;

  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

}
