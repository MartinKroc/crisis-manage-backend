package com.crisis.management.controllers;

import com.crisis.management.dto.*;
import com.crisis.management.models.enums.AlertType;
import com.crisis.management.services.AlertService;
import com.crisis.management.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public AlertDto getAlerts() {
        return alertService.getAlerts();
    }

    @PostMapping
    public ResponseEntity<AddAlertDto> addAlert(@RequestBody AddAlertDto addAlertDto) {
        return alertService.postAlert(addAlertDto);
    }

    @GetMapping("/status/{type}/{id}")
    public ResponseEntity<String> changeAlertStatus(@PathVariable("type")AlertType alertType, @PathVariable("id")long alertId) {
        return alertService.changeAlertStatus(alertType, alertId);
    }

    @PostMapping("sendmail")
    public ResponseEntity<String> sendAlertByEmail(@RequestBody SendAlertEmailDto sendAlertEmailDto) throws MessagingException {
        return mailService.sendAlert(sendAlertEmailDto);
    }

    @PostMapping("sendsms")
    public ResponseEntity<String> sendAlertBySms(@RequestBody SendAlertEmailDto sendAlertEmailDto) {
        return mailService.sendAlertBySms(sendAlertEmailDto);
    }

    @GetMapping("suggestion")
    public List<AlertSuggestionDto> getAlertSug() {
        return alertService.getAlertSuggestions();
    }

    @GetMapping("stat")
    public List<AlertStatsDto> getStats() {
        return alertService.getAlertStats();
    }
}
