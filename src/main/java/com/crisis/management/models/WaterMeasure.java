package com.crisis.management.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long measureId;

    @Column(nullable = false)
    private int waterLevel;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name="station_id")
    private WaterStation station;
}
