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
    private String rzeka;
    private String województwo;
    private String stan_wody;
}
