package com.crisis.management.repo;

import com.crisis.management.models.WeatherMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherMeasureRepo extends JpaRepository<WeatherMeasure, Long> {
}
