package com.crisis.management.services;

import com.crisis.management.dto.AddAlertPropositionDto;
import com.crisis.management.dto.AlertPropositionDto;
import com.crisis.management.dto.DangerTypesDto;
import com.crisis.management.models.Alert;
import com.crisis.management.models.AlertProposition;
import com.crisis.management.models.DangerType;
import com.crisis.management.models.User;
import com.crisis.management.repo.AlertPrepositionRepo;
import com.crisis.management.repo.DangerTypeRepo;
import com.crisis.management.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlertPropositionServiceImpl implements AlertPropositionService {

    private AlertPrepositionRepo alertPrepositionRepo;
    private DangerTypeRepo dangerTypeRepo;
    private UserRepo userRepo;

    @Override
    public List<AlertPropositionDto> getAllAlertPropositions() {
        return alertPrepositionRepo.findAll().stream().map(alertProposition -> AlertPropositionDto.build(alertProposition)).collect(Collectors.toList());
    }

    @Override
    public AlertPropositionDto addAlertProposition(AddAlertPropositionDto addAlertPropositionDto, String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
        DangerType dangerType = dangerTypeRepo.findById(addAlertPropositionDto.getDangerTypeId()).orElseThrow(() -> new RuntimeException("Zagrożenie nie znalezione"));
        AlertProposition alertProposition = AlertProposition.builder()
                .lat(addAlertPropositionDto.getLat())
                .lng(addAlertPropositionDto.getLng())
                .description(addAlertPropositionDto.getDescription())
                .image("test")
                .isAccepted(false)
                .user(user)
                .dangerId(dangerType)
                .build();
        alertPrepositionRepo.save(alertProposition);
        return AlertPropositionDto.build(alertProposition);
    }

    @Override
    public AlertPropositionDto acceptAlertProposition(long alertPropositionId) {
        AlertProposition alertProposition = alertPrepositionRepo.findById(alertPropositionId).orElseThrow(() -> new RuntimeException("Propozycja nie została znaleziona"));
        alertProposition.setAccepted(true);
        alertPrepositionRepo.save(alertProposition);
        return AlertPropositionDto.build(alertProposition);
    }

    @Override
    public AlertPropositionDto addPointToAlertProposition(long alertId) {
        AlertProposition alertProposition = alertPrepositionRepo.findById(alertId).orElseThrow(() -> new RuntimeException("Propozycja nie została znaleziona"));
        alertProposition.setPoints(alertProposition.getPoints()+1);
        alertPrepositionRepo.save(alertProposition);
        return AlertPropositionDto.build(alertProposition);
    }

    @Override
    public List<DangerTypesDto> getAllDangerTypes() {
        return dangerTypeRepo.findAll().stream().map(dangerType -> DangerTypesDto.build(dangerType)).collect(Collectors.toList());
    }
}
