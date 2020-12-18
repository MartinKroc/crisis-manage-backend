package com.crisis.management.repo;

import com.crisis.management.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepo extends JpaRepository<Alert, Long> {
}
