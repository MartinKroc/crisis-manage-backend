package com.crisis.management.services;

import com.crisis.management.dto.*;
import com.crisis.management.models.*;
import com.crisis.management.models.enums.AlertType;
import com.crisis.management.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {

    private WaterMeasureRepo waterMeasureRepo;
    private WeatherMeasureRepo weatherMeasureRepo;

    private WaterAlertRepo waterAlertRepo;
    private WeatherAlertRepo weatherAlertRepo;
    private AlertRepo alertRepo;

    private WaterStationRepo waterStationRepo;
    private WeatherStationRepo weatherStationRepo;

    private DangerTypeRepo dangerTypeRepo;

    @Override
    public List<AlertSuggestionDto> getAlertSuggestions() {
        List<WaterMeasure> waterMeasures = waterMeasureRepo.findAll();
        List<WeatherMeasure> weatherMeasures = weatherMeasureRepo.findAll();
        List<AlertSuggestionDto> alertSuggestions = new ArrayList<AlertSuggestionDto>();
        waterMeasures.forEach(waterMeasure -> {
            if(waterMeasure.getWaterLevel()>waterMeasure.getStation().getAlertLevel()) {
                alertSuggestions.add(AlertSuggestionDto.buildForWater(waterMeasure,"Alert Wodny","Woda przekroczyła poziom alarmowy"));
            }
            else if(waterMeasure.getWaterLevel()>waterMeasure.getStation().getWarningLevel()) {
                alertSuggestions.add(AlertSuggestionDto.buildForWater(waterMeasure,"Ostrzeżenie Wodne","Woda przekroczyła poziom ostrzeżenia"));
            }
        });
        weatherMeasures.forEach(weatherMeasure -> {
            if(weatherMeasure.getSmogLevel()>7) {
                alertSuggestions.add(AlertSuggestionDto.buildForWeather(weatherMeasure,"Smog","Przekroczono bezpieczny poziom smogu"));
            }
            if(weatherMeasure.getTemp()<-2) {
                alertSuggestions.add(AlertSuggestionDto.buildForWeather(weatherMeasure,"Mróz","Przymrozek"));
            }
            else if(weatherMeasure.getTemp()>33) {
                alertSuggestions.add(AlertSuggestionDto.buildForWeather(weatherMeasure,"Upał","Upał"));
            }
        });
        return alertSuggestions;
    }

    @Override
    public AlertDto getAlerts() {
        List<WaterAlertDto> waterAlerts = waterAlertRepo.findAll().stream().map(waterAlert -> WaterAlertDto.build(waterAlert)).collect(Collectors.toList());
        List<WeatherAlertDto> weatherAlerts = weatherAlertRepo.findAll().stream().map(weatherAlert -> WeatherAlertDto.build(weatherAlert)).collect(Collectors.toList());
        List<AlertTableDto> alerts = alertRepo.findAll().stream().map(alert -> AlertTableDto.build(alert)).collect(Collectors.toList());
        AlertDto ss= AlertDto.build(waterAlerts,weatherAlerts,alerts);
        return ss;
    }

    @Override
    public ResponseEntity<AddAlertDto> postAlert(AddAlertDto addAlertDto) {
        if(addAlertDto.getAlertType()== AlertType.WATER) {
            WaterStation waterStation = waterStationRepo.findById(addAlertDto.getWaterStationId()).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            DangerType dangerType = dangerTypeRepo.findById(addAlertDto.getDangerTypeId()).orElseThrow(() -> new RuntimeException("Zagrożenie nie zostało znalezione"));

            WaterAlert waterAlert = WaterAlert.builder()
                    .publishDate(LocalDateTime.now())
                    .isActive(true)
                    .description(addAlertDto.getDescription())
                    .waterDanger(dangerType)
                    .waterStation(waterStation)
                    .build();
            waterAlertRepo.save(waterAlert);

        }
        else if(addAlertDto.getAlertType()== AlertType.WEATHER) {
            WeatherStation weatherStation = weatherStationRepo.findById(addAlertDto.getWaterStationId()).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            DangerType dangerType = dangerTypeRepo.findById(addAlertDto.getDangerTypeId()).orElseThrow(() -> new RuntimeException("Zagrożenie nie zostało znalezione"));

            WeatherAlert weatherAlert = WeatherAlert.builder()
                    .publishDate(LocalDateTime.now())
                    .isActive(true)
                    .description(addAlertDto.getDescription())
                    .weatherDanger(dangerType)
                    .weatherStation(weatherStation)
                    .build();
            weatherAlertRepo.save(weatherAlert);
        }
        else if(addAlertDto.getAlertType()== AlertType.OTHER) {
            DangerType dangerType = dangerTypeRepo.findById(addAlertDto.getDangerTypeId()).orElseThrow(() -> new RuntimeException("Zagrożenie nie zostało znalezione"));

            Alert alert = Alert.builder()
                    .publishDate(LocalDateTime.now())
                    .isActive(true)
                    .lat(addAlertDto.getLat())
                    .lng(addAlertDto.getLng())
                    .description(addAlertDto.getDescription())
                    .dngId(dangerType)
                    .build();
            alertRepo.save(alert);
        }
        return null;
    }

    @Override
    public List<AlertStatsDto> getAlertStats() {
        List<DangerType> dangerTypes = dangerTypeRepo.findAll();
        List<AlertStatsDto> alertStats = new ArrayList<AlertStatsDto>();
        dangerTypes.forEach(dangerType -> {
            int occurrenceWater = dangerType.getWaterAlerts().size();
            int occurrenceWeather = dangerType.getWeatherAlerts().size();
            int occurrenceOther = dangerType.getAlerts().size();
            int occurrence = occurrenceOther + occurrenceWater + occurrenceWeather;
            alertStats.add(AlertStatsDto.build(dangerType,occurrence));
        });
        return alertStats;
    }

    @Override
    public ResponseEntity<String> changeAlertStatus(AlertType alertType, long alertId) {
        if(alertType == AlertType.OTHER) {
            Alert alert = alertRepo.findById(alertId).orElseThrow(() -> new RuntimeException("Alert nie został znaleziony"));
            alert.setActive(false);
            alertRepo.save(alert);
        }
        else if(alertType == AlertType.WATER) {
            WaterAlert waterAlert = waterAlertRepo.findById(alertId).orElseThrow(() -> new RuntimeException("Alert nie został znaleziony"));
            waterAlert.setActive(false);
            waterAlertRepo.save(waterAlert);
        }
        else if(alertType == AlertType.WEATHER) {
            WeatherAlert weatherAlert = weatherAlertRepo.findById(alertId).orElseThrow(() -> new RuntimeException("Alert nie został znaleziony"));
            weatherAlert.setActive(false);
            weatherAlertRepo.save(weatherAlert);
        }
        return ResponseEntity.ok("Zmieniono status alertu");
    }
}
