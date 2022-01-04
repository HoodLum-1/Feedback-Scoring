package com.whereismytransport.FeedbackScoring.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reference_data")
public class ReferenceData {
    @Id
    @Column( name = "route_id", unique = true)
    private String routeId;
    private String metro;
    private String agencyId;
    private String routeName;
}