package com.crisis.management.services;

import com.crisis.management.dto.AddAlertDto;
import com.crisis.management.dto.AlertDto;
import com.crisis.management.dto.AlertSuggestionDto;
import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlertService {
    List<AlertSuggestionDto> getAlertSuggestions();
    AlertDto getAlerts();
    ResponseEntity<AddAlertDto> postAlert(AddAlertDto addAlertDto);
}
