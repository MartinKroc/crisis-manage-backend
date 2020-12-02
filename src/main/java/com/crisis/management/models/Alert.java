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
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "danger_id")
    private DangerType danger;

/*    private WaterMeasure waterMeasure;
    private WeatherMeasure weatherMeasure;*/

}
