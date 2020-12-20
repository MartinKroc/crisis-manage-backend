package com.crisis.management.services;

import com.crisis.management.dto.WaterMeasureDto;
import com.crisis.management.dto.WeatherMeasureDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeasureService {

    List<WaterMeasureDto> getAllWaterMeasures();
    List<WaterMeasureDto> getWaterMeasuresByStationId(long stationId);
    List<WeatherMeasureDto> getWeatherMeasuresByStationId(long stationId);
    String generateRandomMeasure();
}
