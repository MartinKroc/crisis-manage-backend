package com.crisis.management.dto;

import com.crisis.management.models.AlertProposition;
import lombok.Value;

@Value
public class AlertPropositionDto {
    private long alertId;
    private Double lat;
    private Double lng;
    private String description;
    private int points;
    private boolean isAccepted;
    private String username;
    private String dangerTypeName;

    public static AlertPropositionDto build(AlertProposition alertProposition) {
        return new AlertPropositionDto(alertProposition.getAlertId(),alertProposition.getLat(),alertProposition.getLng(),alertProposition.getDescription(),
                alertProposition.getPoints(),alertProposition.isAccepted(),alertProposition.getUser().getUsername(),alertProposition.getDangerId().getDescription());
    }
}
