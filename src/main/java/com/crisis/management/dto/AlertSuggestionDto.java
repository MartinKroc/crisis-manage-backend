package com.crisis.management.dto;

import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WaterStation;
import com.crisis.management.models.WeatherMeasure;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AlertSuggestionDto {
    private String station;
    private String alertType;
    private String description;
    private LocalDateTime date;

    public static AlertSuggestionDto buildForWater(WaterMeasure waterMeasure, String alertType, String description) {
        return new AlertSuggestionDto(waterMeasure.getStation().getName(),alertType,description, waterMeasure.getDate());
    };
    public static AlertSuggestionDto buildForWeather(WeatherMeasure weatherMeasure, String alertType, String description) {
        return new AlertSuggestionDto(weatherMeasure.getWeatherstation().getName(),alertType,description, weatherMeasure.getDate());
    };
}
