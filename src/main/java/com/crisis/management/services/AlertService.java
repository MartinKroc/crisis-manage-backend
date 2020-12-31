package com.crisis.management.services;

import com.crisis.management.dto.AddAlertDto;
import com.crisis.management.dto.AlertDto;
import com.crisis.management.dto.AlertStatsDto;
import com.crisis.management.dto.AlertSuggestionDto;
import com.crisis.management.models.enums.AlertType;
import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlertService {
    List<AlertSuggestionDto> getAlertSuggestions();
    AlertDto getAlerts();
    ResponseEntity<AddAlertDto> postAlert(AddAlertDto addAlertDto);
    List<AlertStatsDto> getAlertStats();
    ResponseEntity<String> changeAlertStatus(AlertType alertType, long alertId);
}
