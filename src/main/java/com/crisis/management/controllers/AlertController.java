package com.crisis.management.controllers;

import com.crisis.management.dto.AddAlertDto;
import com.crisis.management.dto.AlertDto;
import com.crisis.management.dto.AlertSuggestionDto;
import com.crisis.management.services.AlertService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/alert")
@AllArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public AlertDto getAlerts() {
        return alertService.getAlerts();
    }

    @PostMapping
    public ResponseEntity<AddAlertDto> addAlert(@RequestBody AddAlertDto addAlertDto) {
        return alertService.postAlert(addAlertDto);
    }

    @DeleteMapping
    public String deleteAlert() {
        return null;
    }

    @PutMapping
    public AlertDto putAlert() {
        return null;
    }

    @GetMapping("suggestion")
    public List<AlertSuggestionDto> getAlertSug() {
        return alertService.getAlertSuggestions();
    }
}
