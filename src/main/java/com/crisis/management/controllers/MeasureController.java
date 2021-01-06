package com.crisis.management.controllers;

import com.crisis.management.dto.WaterMeasureDto;
import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherMeasureDto;
import com.crisis.management.services.MeasureService;
import com.crisis.management.services.WaterStationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/measure")
@AllArgsConstructor
public class MeasureController {

    private final MeasureService measureService;

    @GetMapping("/water")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<WaterMeasureDto> getWaterMeasures() {
        return measureService.getAllWaterMeasures();
    }

    @GetMapping("/water/{stationId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<WaterMeasureDto> getWaterMeasuresByStationId(@PathVariable("stationId") long stationId) {
        return measureService.getWaterMeasuresByStationId(stationId);
    }

    @GetMapping("/weather/{stationId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<WeatherMeasureDto> getWeatherMeasuresByStationId(@PathVariable("stationId") long stationId) {
        return measureService.getWeatherMeasuresByStationId(stationId);
    }

    @GetMapping("/generate")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String generateRandomMeasures() {
        return measureService.generateRandomMeasure();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
