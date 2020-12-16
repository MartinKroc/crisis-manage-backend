package com.crisis.management.controllers;

import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherStationDto;
import com.crisis.management.models.WeatherStation;
import com.crisis.management.services.WaterStationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/station")
@AllArgsConstructor
public class StationController {

    private final WaterStationService waterStationService;

    @GetMapping("/water")
    public List<WaterStationDto> getWaterStations() {
        return waterStationService.getWaterStations();
    }

    @GetMapping("/weather")
    public List<WeatherStationDto> getWeatherStations() {
        return waterStationService.getWeatherStations();
    }

    @GetMapping("/water/{stationId}")
    public WaterStationDto getWaterStationById(@PathVariable("stationId") long stationId) {
        return waterStationService.getWaterStationById(stationId);
    }

    @GetMapping("/weather/{stationId}")
    public WeatherStationDto getWeatherStationById(@PathVariable("stationId") long stationId) {
        return waterStationService.getWeatherStationById(stationId);
    }
}
