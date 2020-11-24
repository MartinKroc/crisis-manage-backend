package com.crisis.management.repo;

import com.crisis.management.models.WaterStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterStationRepo  extends JpaRepository<WaterStation, Long> {
}
