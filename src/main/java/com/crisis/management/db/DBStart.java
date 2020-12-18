package com.crisis.management.db;

import com.crisis.management.models.*;
import com.crisis.management.repo.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DBStart implements ApplicationRunner {

    WaterStationRepo waterStationRepo;
    WeatherStationRepo weatherStationRepo;
    WaterMeasureRepo waterMeasureRepo;
    DangerTypeRepo dangerTypeRepo;

    @Autowired
    public DBStart(WaterStationRepo waterStationRepo, WeatherStationRepo weatherStationRepo, WaterMeasureRepo waterMeasureRepo, DangerTypeRepo dangerTypeRepo) {
        this.waterStationRepo = waterStationRepo;
        this.weatherStationRepo = weatherStationRepo;
        this.waterMeasureRepo = waterMeasureRepo;
        this.dangerTypeRepo = dangerTypeRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (waterStationRepo.count() <= 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<WaterStation> stations = mapper.readValue(
                        new File("src/main/resources/water.json"),
                        new TypeReference<List<WaterStation>>() {
                        }
                );
                stations.forEach(el -> waterStationRepo.save(el));
            } catch (Error e) {
                System.out.println("Bład dodawania do bazy danych");
            }
        }
        if (weatherStationRepo.count() <= 0) {
            try {
                ObjectMapper mapperTwo = new ObjectMapper();
                List<WeatherStation> weatherStations = mapperTwo.readValue(
                        new File("src/main/resources/weather.json"),
                        new TypeReference<List<WeatherStation>>() {
                        }
                );
                weatherStations.forEach(el -> weatherStationRepo.save(el));
            } catch (Error e) {
                System.out.println("Bład dodawania do bazy danych");
            }
        }
        if (dangerTypeRepo.count() <= 0) {
            try {
                ObjectMapper mapperThree = new ObjectMapper();
                List<DangerType> dangerTypes = mapperThree.readValue(
                        new File("src/main/resources/dangers.json"),
                        new TypeReference<List<DangerType>>() {
                        }
                );
                dangerTypes.forEach(el -> dangerTypeRepo.save(el));
            } catch (Error e) {
                System.out.println("Bład dodawania do bazy danych");
            }
        }
    }

}
