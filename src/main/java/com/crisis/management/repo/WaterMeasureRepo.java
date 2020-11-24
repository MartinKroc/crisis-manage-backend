package com.crisis.management.repo;

import com.crisis.management.models.WaterMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterMeasureRepo extends JpaRepository<WaterMeasure, Long> {
}
