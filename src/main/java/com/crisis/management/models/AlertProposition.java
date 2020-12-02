package com.crisis.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertProposition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long alertId;

    @Column(nullable = false, unique = true)
    private String cords;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String image;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "danger_id")
    private DangerType dangerId;
}
