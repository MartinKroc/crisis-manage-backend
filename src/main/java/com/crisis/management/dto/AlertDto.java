package com.crisis.management.dto;

import lombok.Value;

import java.util.List;

@Value
public class AlertDto {
    private List<WaterAlertDto> waterAlert;
    private List<WeatherAlertDto> weatherAlert;

    public static AlertDto build(List<WaterAlertDto> waterAlertDto, List<WeatherAlertDto> weatherAlertDto) {
        return new AlertDto(waterAlertDto,weatherAlertDto);
    };
}
