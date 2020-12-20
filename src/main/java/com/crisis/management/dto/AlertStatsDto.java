package com.crisis.management.dto;

import com.crisis.management.models.DangerType;
import lombok.Value;

@Value
public class AlertStatsDto {
    private String dangerType;
    private int occurrence;

    public static AlertStatsDto build(DangerType dangerType, int occur) {
        return new AlertStatsDto(dangerType.getDescription(), occur);
    }
}
