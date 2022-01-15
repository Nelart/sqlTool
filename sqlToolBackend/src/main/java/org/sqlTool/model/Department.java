package org.sqlTool.model;
/**
 * @COPYRIGHT (C) 2021 Renewal 365
 *
 * <p>All rights reserved
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/** Created by Edward on 7/26/2021. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENTS")
public class Department implements Serializable {

  @Id
  private long departmentId;

  private String departmentName;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

}
