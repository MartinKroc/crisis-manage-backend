package com.crisis.management.services;

import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherStationDto;
import com.crisis.management.models.WaterStation;
import com.crisis.management.repo.WaterStationRepo;
import com.crisis.management.repo.WeatherStationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaterStationServiceImpl implements WaterStationService {

    public WaterStationRepo waterStationRepo;
    public WeatherStationRepo weatherStationRepo;

    @Override
    public List<WaterStationDto> getWaterStations() {
        return waterStationRepo.findAll().stream().map(waterStation -> WaterStationDto.build(waterStation)).collect(Collectors.toList());
    }

    @Override
    public List<WeatherStationDto> getWeatherStations() {
        return weatherStationRepo.findAll().stream().map(weatherStation -> WeatherStationDto.build(weatherStation)).collect(Collectors.toList());
    }
}
