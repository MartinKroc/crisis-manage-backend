package com.crisis.management.controllers;

import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherStationDto;
import com.crisis.management.services.WaterStationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/station")
@AllArgsConstructor
public class StationController {

    private final WaterStationService waterStationService;

    @GetMapping("/water")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<WaterStationDto> getWaterStations() {
        return waterStationService.getWaterStations();
    }

    @GetMapping("/weather")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<WeatherStationDto> getWeatherStations() {
        return waterStationService.getWeatherStations();
    }

    @GetMapping("/water/{stationId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public WaterStationDto getWaterStationById(@PathVariable("stationId") long stationId) {
        return waterStationService.getWaterStationById(stationId);
    }

    @GetMapping("/weather/{stationId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public WeatherStationDto getWeatherStationById(@PathVariable("stationId") long stationId) {
        return waterStationService.getWeatherStationById(stationId);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
