package com.crisis.management.dto;

import com.crisis.management.models.WaterStation;
import lombok.Value;

@Value
public class WaterStationDto {
    private String cords;
    private String name;
    private int alertLevel;
    private int warningLevel;

    public static WaterStationDto build(WaterStation waterStation) {
        return new WaterStationDto(waterStation.getCords(),waterStation.getName(),waterStation.getAlertLevel(),waterStation.getWarningLevel());
    };
}
