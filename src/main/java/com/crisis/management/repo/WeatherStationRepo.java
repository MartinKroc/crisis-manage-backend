package com.crisis.management.repo;

import com.crisis.management.models.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherStationRepo extends JpaRepository<WeatherStation, Long> {
}
