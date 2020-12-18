package com.crisis.management.dto;

import com.crisis.management.models.DangerType;
import com.crisis.management.models.WaterAlert;
import com.crisis.management.models.WeatherAlert;
import com.crisis.management.models.WeatherStation;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WeatherAlertDto {
    private LocalDateTime publishDate;
    private boolean isActive;
    private String description;
    private int dangerScale;
    private String weatherStationName;

    public static WeatherAlertDto build(WeatherAlert weatherAlert) {
        return new WeatherAlertDto(weatherAlert.getPublishDate(),weatherAlert.isActive(),weatherAlert.getWeatherDanger().getDescription(),weatherAlert.getWeatherDanger().getDangerScale(),weatherAlert.getWeatherStation().getName());
    };
}
