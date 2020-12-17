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
    private DangerType weatherDanger;
    private WeatherStation weatherStation;

    public static WeatherAlertDto build(WeatherAlert weatherAlert) {
        return new WeatherAlertDto(weatherAlert.getPublishDate(),weatherAlert.isActive(),weatherAlert.getWeatherDanger(),weatherAlert.getWeatherStation());
    };
}
