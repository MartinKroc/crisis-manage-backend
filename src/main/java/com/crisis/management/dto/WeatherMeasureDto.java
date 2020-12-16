package com.crisis.management.dto;

import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WeatherMeasure;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WeatherMeasureDto {
    private int temp;
    private int pressure;
    private int smogLevel;
    private int humidity;
    private LocalDateTime date;

    public static WeatherMeasureDto build(WeatherMeasure weatherMeasure) {
        return new WeatherMeasureDto(weatherMeasure.getTemp(), weatherMeasure.getPressure(), weatherMeasure.getSmogLevel(), weatherMeasure.getHumidity(), weatherMeasure.getDate());
    };
}
