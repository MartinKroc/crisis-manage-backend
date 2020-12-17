package com.crisis.management.services;

import com.crisis.management.dto.*;
import com.crisis.management.models.*;
import com.crisis.management.models.enums.AlertType;
import com.crisis.management.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {

    private WaterMeasureRepo waterMeasureRepo;
    private WeatherMeasureRepo weatherMeasureRepo;

    private WaterAlertRepo waterAlertRepo;
    private WeatherAlertRepo weatherAlertRepo;

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
        AlertDto ss= AlertDto.build(waterAlerts,weatherAlerts);
        return ss;
    }

    @Override
    public ResponseEntity<AddAlertDto> postAlert(AddAlertDto addAlertDto) {
        if(addAlertDto.getAlertType()== AlertType.WATER) {
            WaterStation waterStation = waterStationRepo.findById(addAlertDto.getWaterStationId()).orElseThrow(() -> new RuntimeException("Stacja nie została znaleziona"));
            DangerType dangerType = dangerTypeRepo.findById(addAlertDto.getDangerTypeId()).orElseThrow(() -> new RuntimeException("Zagrożenie nie zostało znalezione"));

        }
        return null;
    }
}
