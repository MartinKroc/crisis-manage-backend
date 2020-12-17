package com.crisis.management.dto;

import com.crisis.management.models.enums.AlertType;
import lombok.Value;

@Value
public class AddAlertDto {
    private AlertType alertType;
    private long measureId;
    private long dangerTypeId;
    private long waterStationId;
}
