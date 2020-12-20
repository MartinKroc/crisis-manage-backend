package com.crisis.management.services;

import com.crisis.management.dto.WaterMeasureDto;
import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherMeasureDto;
import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WaterStation;
import com.crisis.management.models.WeatherMeasure;
import com.crisis.management.models.WeatherStation;
import com.crisis.management.repo.WaterMeasureRepo;
import com.crisis.management.repo.WaterStationRepo;
import com.crisis.management.repo.WeatherMeasureRepo;
import com.crisis.management.repo.WeatherStationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
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

    @Override
    public String generateRandomMeasure() {
/*        List<WaterMeasure> waterMeasures = waterMeasureRepo.findAll();
        List<WeatherMeasure> weatherMeasures = weatherMeasureRepo.findAll();*/

/*        waterMeasureRepo.deleteAll();
        weatherMeasureRepo.deleteAll();*/
        long i,j;
        int minusHour;
        for(i=1;i<=9;i++) {
            WaterStation waterStation = waterStationRepo.findById(i).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            minusHour = 4;
            for(j=1;j<=5;j++) {
                WaterMeasure waterMeasure = WaterMeasure.builder()
                        .date(LocalDateTime.now().minusHours(minusHour))
                        .waterLevel((int)(Math.random() * waterStation.getWarningLevel()+10 + waterStation.getWarningLevel()-40))
                        .station(waterStation)
                        .build();
                System.out.println(waterMeasure.getDate());
                System.out.println(waterMeasure.getWaterLevel());
                minusHour+=4;
            }
        }

        for(i=1;i<=12;i++) {
            WeatherStation weatherStation = weatherStationRepo.findById(i).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            minusHour = 4;
            for(j=1;j<=5;j++) {
                WeatherMeasure weatherMeasure = WeatherMeasure.builder()
                        .date(LocalDateTime.now().minusHours(minusHour))
                        .humidity((int)(Math.random() * 100 + 5))
                        .pressure((int)(Math.random() * 1020 + 990))
                        .smogLevel((int)(Math.random() * 10 + 0))
                        .temp((int)(Math.random() * 15 + -4))
                        .weatherstation(weatherStation)
                        .build();
                System.out.println(weatherMeasure.getDate());
                minusHour+=4;
            }
        }
        return null;
    }
}
