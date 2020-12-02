package com.crisis.management.repo;

import com.crisis.management.models.WaterStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaterStationRepo extends JpaRepository<WaterStation, Long> {

}
