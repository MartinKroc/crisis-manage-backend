package com.crisis.management.dto;

import com.crisis.management.models.enums.AlertType;
import lombok.Value;

import java.util.List;

@Value
public class AddAlertDto {
    private AlertType alertType;
    private String description;
    private long dangerTypeId;
    private long waterStationId;
    private Double lat;
    private Double lng;
}
