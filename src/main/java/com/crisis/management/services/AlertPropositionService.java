package com.crisis.management.services;

import com.crisis.management.dto.AddAlertPropositionDto;
import com.crisis.management.dto.AlertPropositionDto;
import com.crisis.management.dto.DangerTypesDto;

import java.util.List;

public interface AlertPropositionService {

    List<AlertPropositionDto> getAllAlertPropositions();

    AlertPropositionDto addAlertProposition(AddAlertPropositionDto addAlertPropositionDto, String username);

    AlertPropositionDto acceptAlertProposition(long alertPropositionId);

    AlertPropositionDto addPointToAlertProposition(long alertId);

    List<DangerTypesDto> getAllDangerTypes();
}
