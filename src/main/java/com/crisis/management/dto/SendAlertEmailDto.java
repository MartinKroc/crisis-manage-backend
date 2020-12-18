package com.crisis.management.dto;

import com.crisis.management.models.enums.AlertType;
import lombok.Value;

@Value
public class SendAlertEmailDto {
    private AlertType alertType;
    private long alertId;
}
