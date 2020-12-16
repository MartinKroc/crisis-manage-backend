package com.crisis.management.services;

import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherStationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WaterStationService {
    List<WaterStationDto> getWaterStations();
    List<WeatherStationDto> getWeatherStations();
    WaterStationDto getWaterStationById(long stationId);
    WeatherStationDto getWeatherStationById(long stationId);
}
