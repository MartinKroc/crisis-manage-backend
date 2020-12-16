package com.crisis.management.controllers;

import com.crisis.management.dto.WaterMeasureDto;
import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherMeasureDto;
import com.crisis.management.services.MeasureService;
import com.crisis.management.services.WaterStationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/measure")
@AllArgsConstructor
public class MeasureController {

    private final MeasureService measureService;
    private final WaterStationService waterStationService;

    @GetMapping("/water")
    public List<WaterMeasureDto> getWaterMeasures() {
        return measureService.getAllWaterMeasures();
    }

    @GetMapping("/water/{stationId}")
    public List<WaterMeasureDto> getWaterMeasuresByStationId(@PathVariable("stationId") long stationId) {
        return measureService.getWaterMeasuresByStationId(stationId);
    }

    @GetMapping("/weather/{stationId}")
    public List<WeatherMeasureDto> getWeatherMeasuresByStationId(@PathVariable("stationId") long stationId) {
        return measureService.getWeatherMeasuresByStationId(stationId);
    }
}
