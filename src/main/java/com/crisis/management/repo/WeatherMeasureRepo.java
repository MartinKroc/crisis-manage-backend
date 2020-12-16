package com.crisis.management.repo;

import com.crisis.management.models.WeatherMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherMeasureRepo extends JpaRepository<WeatherMeasure, Long> {
    List<WeatherMeasure> findAllByWeatherstationId(long stationId);
}
