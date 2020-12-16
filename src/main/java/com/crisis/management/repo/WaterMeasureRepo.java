package com.crisis.management.repo;

import com.crisis.management.models.WaterMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaterMeasureRepo extends JpaRepository<WaterMeasure, Long> {
    List<WaterMeasure> findAllByStationId(long stationId);
}
