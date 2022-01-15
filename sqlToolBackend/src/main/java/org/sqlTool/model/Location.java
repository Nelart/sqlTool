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
@Table(name = "LOCATIONS")
public class Location implements Serializable {

  @Id
  private long locationId;

  private String streetAddress;
  private String postalCode;
  private String city;
  private String stateProvince;

  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

}
