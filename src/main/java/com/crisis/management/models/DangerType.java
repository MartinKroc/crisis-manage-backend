package com.crisis.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangerType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dangerId;

    @Column(nullable = false)
    private int dangerScale;

    @Column(nullable = true)
    private String description;
}
