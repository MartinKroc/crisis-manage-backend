package com.crisis.management.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long measureId;

    @Column(nullable = false)
    private int temp;

    @Column(nullable = false)
    private int pressure;

    @Column(nullable = false)
    private int smogLevel;

    @Column(nullable = false)
    private int humidity;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name = "station_id")
    private WeatherStation weatherstation;
}
