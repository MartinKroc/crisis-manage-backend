package com.crisis.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long measureId;

    @Column(nullable = false)
    private int waterLevel;

    @Column(nullable = false)
    private LocalDateTime date;

    private long stationId;
}
