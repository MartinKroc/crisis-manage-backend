package com.crisis.management.dto;

import com.crisis.management.models.WaterStation;
import lombok.Value;

@Value
public class WaterStationDto {
    private long id;
    private Double lat;
    private Double lng;
    private String name;
    private int alertLevel;
    private int warningLevel;

    public static WaterStationDto build(WaterStation waterStation) {
        return new WaterStationDto(waterStation.getId(),waterStation.getLat(),waterStation.getLng(),waterStation.getName(),waterStation.getAlertLevel(),waterStation.getWarningLevel());
    };
}
