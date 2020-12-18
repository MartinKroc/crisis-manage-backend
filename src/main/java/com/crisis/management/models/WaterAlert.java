package com.crisis.management.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime publishDate;

    @Column(nullable = false)
    private boolean isActive;

    private String description;

    @ManyToOne
    @JoinColumn(name = "danger_id")
    private DangerType waterDanger;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private WaterStation waterStation;
}
