package com.crisis.management.dto;

import com.crisis.management.models.WeatherStation;
import lombok.Value;

@Value
public class WeatherStationDto {
    private String cords;
    private String name;

    public static WeatherStationDto build(WeatherStation weatherStation) {
        return new WeatherStationDto(weatherStation.getCords(), weatherStation.getName());
    }
}
