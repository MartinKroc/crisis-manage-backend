package com.crisis.management.controllers;

import com.crisis.management.dto.*;
import com.crisis.management.models.enums.AlertType;
import com.crisis.management.services.AlertService;
import com.crisis.management.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/alert")
@AllArgsConstructor
public class AlertController {

    private final AlertService alertService;
    private final MailService mailService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public AlertDto getAlerts() {
        return alertService.getAlerts();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public long addAlert(@RequestBody AddAlertDto addAlertDto) {
        return alertService.postAlert(addAlertDto);
    }

    @GetMapping("/status/{type}/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public long changeAlertStatus(@PathVariable("type")AlertType alertType, @PathVariable("id")long alertId) {
        return alertService.changeAlertStatus(alertType, alertId);
    }

    @PostMapping("sendmail")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> sendAlertByEmail(@RequestBody SendAlertEmailDto sendAlertEmailDto) throws MessagingException {
        return mailService.sendAlert(sendAlertEmailDto);
    }

    @PostMapping("sendsms")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> sendAlertBySms(@RequestBody SendAlertEmailDto sendAlertEmailDto) {
        return mailService.sendAlertBySms(sendAlertEmailDto);
    }

    @GetMapping("suggestion")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<AlertSuggestionDto> getAlertSug() {
        return alertService.getAlertSuggestions();
    }

    @GetMapping("stat")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<AlertStatsDto> getStats() {
        return alertService.getAlertStats();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
