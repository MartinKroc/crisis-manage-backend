package com.crisis.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private Double lat;

    @Column(nullable = false, unique = true)
    private Double lng;

    @Column(nullable = false)
    private String weatherTown;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "weatherstation")
    private List<WeatherMeasure> measures;

    @OneToMany(mappedBy = "weatherStation")
    private List<WeatherAlert> weatherAlerts;
}
