package com.crisis.management.repo;

import com.crisis.management.models.DangerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DangerTypeRepo extends JpaRepository<DangerType, Long> {
}
