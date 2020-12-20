package com.crisis.management.controllers;

import com.crisis.management.dto.AddAlertPropositionDto;
import com.crisis.management.dto.AlertPropositionDto;
import com.crisis.management.dto.DangerTypesDto;
import com.crisis.management.services.AlertPropositionService;
import com.crisis.management.utilities.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/proposition")
@AllArgsConstructor
public class AlertPropositionController {

    private final AlertPropositionService alertPropositionService;

    @GetMapping()
    public List<AlertPropositionDto> getAlertPropositions() {
        return alertPropositionService.getAllAlertPropositions();
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public AlertPropositionDto postAlertProposition(@RequestBody AddAlertPropositionDto addAlertPropositionDto, Authentication authentication) {
        return alertPropositionService.addAlertProposition(addAlertPropositionDto, AuthMiner.getUsername(authentication));
    }

    @GetMapping("accept/{AlertPropositionId}")
    public AlertPropositionDto acceptAlertProp(@PathVariable("AlertPropositionId")long alertPropositionId) {
        return alertPropositionService.acceptAlertProposition(alertPropositionId);
    }

    @GetMapping("point/{AlertProposition}")
    public AlertPropositionDto addPointToAlertProp(@PathVariable("AlertProposition")long alertId) {
        return alertPropositionService.addPointToAlertProposition(alertId);
    }

    @GetMapping("dangers")
    public List<DangerTypesDto> getDangerTypes() {
        return alertPropositionService.getAllDangerTypes();
    }

}
