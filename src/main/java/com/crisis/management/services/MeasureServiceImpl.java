package com.crisis.management.services;

import com.crisis.management.dto.WaterMeasureDto;
import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherMeasureDto;
import com.crisis.management.models.*;
import com.crisis.management.repo.WaterMeasureRepo;
import com.crisis.management.repo.WaterStationRepo;
import com.crisis.management.repo.WeatherMeasureRepo;
import com.crisis.management.repo.WeatherStationRepo;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        List<WaterMeasure> waterMeasures = waterMeasureRepo.findAll();
        List<WeatherMeasure> weatherMeasures = weatherMeasureRepo.findAll();

        waterMeasureRepo.deleteAll();
        weatherMeasureRepo.deleteAll();
        long i,j;
        int minusHour;
        for(i=1;i<=9;i++) {
            WaterStation waterStation = waterStationRepo.findById(i).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            minusHour = 4;
            for(j=1;j<=5;j++) {
                LocalDateTime tempDate = LocalDateTime.now().minusHours(minusHour);
                if(tempDate.getHour()==23 || tempDate.getHour()==22 || tempDate.getHour()==21 || tempDate.getHour()==20) {
                    tempDate.minusDays(1);
                }
                WaterMeasure waterMeasure = WaterMeasure.builder()
                        .date(tempDate)
                        .waterLevel((int)(Math.random() * waterStation.getWarningLevel()+10 + waterStation.getWarningLevel()-80))
                        .station(waterStation)
                        .build();
                waterMeasureRepo.save(waterMeasure);
                minusHour+=4;
            }
        }

        for(i=1;i<=12;i++) {
            WeatherStation weatherStation = weatherStationRepo.findById(i).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            minusHour = 4;
            for(j=1;j<=5;j++) {
                LocalDateTime tempDate = LocalDateTime.now().minusHours(minusHour);
                if(tempDate.getHour()==23 || tempDate.getHour()==22 || tempDate.getHour()==21 || tempDate.getHour()==20) {
                    tempDate.minusDays(1);
                }
                WeatherMeasure weatherMeasure = WeatherMeasure.builder()
                        .date(tempDate)
                        .humidity((int)(Math.random() * 100 + 5))
                        .pressure((int)(Math.random() * 1020 + 990))
                        .smogLevel((int)(Math.random() * 8 + 0))
                        .temp((int)(Math.random() * 20 + -3))
                        .weatherstation(weatherStation)
                        .build();
                weatherMeasureRepo.save(weatherMeasure);
                minusHour+=4;
            }
        }
        return null;
    }
/*    @Scheduled(fixedRate = 240000)
    public void updateMeasures() {
        String uri = "https://danepubliczne.imgw.pl/api/data/hydro/";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<WaterM>> response = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<WaterM>>() {
        });
        response.getBody().forEach(el -> {
            if(el.getId_stacji().equals("150200090")) {
                WaterStation station = waterStationRepo.findById(Long.parseLong("9")).orElseThrow(() -> new RuntimeException("Nie znaleziono stacji pogodowej o id"));
                WaterMeasure waterMeasure = WaterMeasure.builder()
                        .waterLevel(Integer.parseInt(el.getStan_wody()))
                        .date(LocalDateTime.parse(el.getStan_wody_data_pomiaru(),formatter))
                        .station(station)
                        .build();
                waterMeasureRepo.save(waterMeasure);
            }
        });
    }*/
}
