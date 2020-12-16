package com.crisis.management.services;

import com.crisis.management.dto.WaterMeasureDto;
import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherMeasureDto;
import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WeatherMeasure;
import com.crisis.management.repo.WaterMeasureRepo;
import com.crisis.management.repo.WaterStationRepo;
import com.crisis.management.repo.WeatherMeasureRepo;
import com.crisis.management.repo.WeatherStationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeasureServiceImpl implements MeasureService {

    public WaterStationRepo waterStationRepo;
    public WeatherStationRepo weatherStationRepo;
    public WaterMeasureRepo waterMeasureRepo;
    public WeatherMeasureRepo weatherMeasureRepo;

    @Override
    public List<WaterMeasureDto> getAllWaterMeasures() {
        return waterMeasureRepo.findAll().stream().map(waterMeasure -> WaterMeasureDto.build(waterMeasure)).collect(Collectors.toList());
    }

    @Override
    public List<WaterMeasureDto> getWaterMeasuresByStationId(long stationId) {
        List<WaterMeasure> list = waterMeasureRepo.findAllByStationId(stationId);
        return list.stream().map(measure -> WaterMeasureDto.build(measure)).collect(Collectors.toList());
    }

    @Override
    public List<WeatherMeasureDto> getWeatherMeasuresByStationId(long stationId) {
        List<WeatherMeasure> list = weatherMeasureRepo.findAllByWeatherstationId(stationId);
        return list.stream().map(measure -> WeatherMeasureDto.build(measure)).collect(Collectors.toList());
    }
}
