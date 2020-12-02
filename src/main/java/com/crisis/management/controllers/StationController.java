package com.crisis.management.controllers;

import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherStationDto;
import com.crisis.management.services.WaterStationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("weather")
    public List<WeatherStationDto> getWeatherStations() {
        return waterStationService.getWeatherStations();
    }
}
