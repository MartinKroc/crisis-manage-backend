package com.crisis.management.dto;

import com.crisis.management.models.Alert;
import com.crisis.management.models.DangerType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AlertTableDto {
    private LocalDateTime publishDate;
    private boolean isActive;
    private String lat;
    private String lng;
    private String description;
    private String dangerDescription;
    private int dangerScale;

    public static AlertTableDto build(Alert alert) {
        return new AlertTableDto(alert.getPublishDate(), alert.isActive(),alert.getLat(),alert.getLng(),alert.getDescription(),alert.getDngId().getDescription(),alert.getDngId().getDangerScale());
    };
}
