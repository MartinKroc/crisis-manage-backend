package com.crisis.management.services;

import com.crisis.management.dto.WaterStationDto;
import com.crisis.management.dto.WeatherStationDto;
import com.crisis.management.models.WaterM;
import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WaterStation;
import com.crisis.management.repo.WaterMeasureRepo;
import com.crisis.management.repo.WaterStationRepo;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaterStationServiceImpl implements WaterStationService {

    public WaterStationRepo waterStationRepo;
    public WeatherStationRepo weatherStationRepo;

    public WaterMeasureRepo waterMeasureRepo;

    @Override
    public List<WaterStationDto> getWaterStations() {
        return waterStationRepo.findAll().stream().map(waterStation -> WaterStationDto.build(waterStation)).collect(Collectors.toList());
    }

    @Override
    public List<WeatherStationDto> getWeatherStations() {
        return weatherStationRepo.findAll().stream().map(weatherStation -> WeatherStationDto.build(weatherStation)).collect(Collectors.toList());
    }

    @Override
    public WaterStationDto getWaterStationById(long stationId) {
        return WaterStationDto.build(waterStationRepo.findById(stationId).orElseThrow(() -> new RuntimeException("Nie znaleziono stacji wodnej o id" + stationId)));
    }

    @Override
    public WeatherStationDto getWeatherStationById(long stationId) {
        return WeatherStationDto.build(weatherStationRepo.findById(stationId).orElseThrow(() -> new RuntimeException("Nie znaleziono stacji pogodowej o id" + stationId)));
    }

    @Override
    public ResponseEntity<List<WaterM>> updateWaterStations() {
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
        return response;
    }
}
