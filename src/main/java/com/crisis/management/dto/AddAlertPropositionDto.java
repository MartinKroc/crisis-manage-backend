package com.crisis.management.dto;

import lombok.Value;

@Value
public class AddAlertPropositionDto {
    private Double lat;
    private Double lng;
    private String description;
    private long dangerTypeId;
}
