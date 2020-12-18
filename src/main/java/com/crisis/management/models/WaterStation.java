package com.crisis.management.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private Double lat;
    @Column(nullable = false, unique = true)
    private Double lng;

    @Column(nullable = false)
    private String name;

    @Column
    private int alertLevel;

    @Column
    private int warningLevel;

    @OneToMany(mappedBy = "station")
    private List<WaterMeasure> measures;

    @OneToMany(mappedBy = "waterStation")
    private List<WaterAlert> waterAlerts;
}
