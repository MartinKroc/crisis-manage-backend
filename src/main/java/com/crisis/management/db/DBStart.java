package com.crisis.management.db;

import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WaterStation;
import com.crisis.management.models.WeatherMeasure;
import com.crisis.management.models.WeatherStation;
import com.crisis.management.repo.WaterMeasureRepo;
import com.crisis.management.repo.WaterStationRepo;
import com.crisis.management.repo.WeatherMeasureRepo;
import com.crisis.management.repo.WeatherStationRepo;
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

    @Autowired
    public DBStart(WaterStationRepo waterStationRepo, WeatherStationRepo weatherStationRepo, WaterMeasureRepo waterMeasureRepo) {
        this.waterStationRepo = waterStationRepo;
        this.weatherStationRepo = weatherStationRepo;
        this.waterMeasureRepo = waterMeasureRepo;
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
    }

}
