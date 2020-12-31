package com.crisis.management.dto;

import com.crisis.management.models.WeatherStation;
import lombok.Value;

@Value
public class WeatherStationDto {
    private long id;
    private Double lat;
    private Double lng;
    private String name;

    public static WeatherStationDto build(WeatherStation weatherStation) {
        return new WeatherStationDto(weatherStation.getId(),weatherStation.getLat(), weatherStation.getLng(), weatherStation.getName());
    }
}
