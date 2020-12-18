package com.crisis.management.controllers;

import com.crisis.management.dto.AddAlertDto;
import com.crisis.management.dto.AlertDto;
import com.crisis.management.dto.AlertSuggestionDto;
import com.crisis.management.dto.SendAlertEmailDto;
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

    @PostMapping("sendmail")
    public ResponseEntity<String> sendAlertByEmail(@RequestBody SendAlertEmailDto sendAlertEmailDto) throws MessagingException {
        return mailService.sendAlert(sendAlertEmailDto);
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
