package com.crisis.management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WaterM {
    private String id_stacji;
    private String stacja;
    private String stan_wody;
    private String stan_wody_data_pomiaru;
}
