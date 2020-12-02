package com.crisis.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long measureId;

    @Column(nullable = false)
    private int temp;

    @Column(nullable = false)
    private int pressure;

    @Column(nullable = false)
    private int smogLevel;

    @Column(nullable = false)
    private int humidity;

    @ManyToOne()
    @JoinColumn(name = "station_id")
    private WeatherStation weatherstation;
}
