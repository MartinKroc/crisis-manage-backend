package com.crisis.management.repo;

import com.crisis.management.models.AlertProposition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertPrepositionRepo extends JpaRepository<AlertProposition, Long> {
    Optional<List<AlertProposition>> findAllByUserId(Long userId);
}
