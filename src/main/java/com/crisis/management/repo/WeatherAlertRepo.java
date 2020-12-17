package com.crisis.management.repo;

import com.crisis.management.models.WeatherAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherAlertRepo extends JpaRepository<WeatherAlert, Long> {
}
