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
public class DangerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dangerId;

    @Column(nullable = false)
    private int dangerScale;

    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "waterDanger")
    private List<WaterAlert> waterAlerts;

    @OneToMany(mappedBy = "weatherDanger")
    private List<WeatherAlert> weatherAlerts;

    @OneToMany(mappedBy = "dangerId")
    private List<AlertProposition> alertPropositions;

    @OneToMany(mappedBy = "dngId")
    private List<Alert> alerts;
}
