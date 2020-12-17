package com.crisis.management.repo;

import com.crisis.management.models.WaterAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterAlertRepo extends JpaRepository<WaterAlert, Long> {
}
