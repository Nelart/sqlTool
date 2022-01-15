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
@Table(name = "COUNTRIES")
public class Country implements Serializable {

  @Id
  private String countryId;

  private String countryName;

  @ManyToOne
  @JoinColumn(name = "region_id")
  private Region region;

}
