package com.crisis.management.dto;

import com.crisis.management.models.DangerType;
import com.crisis.management.models.WaterAlert;
import com.crisis.management.models.WaterMeasure;
import com.crisis.management.models.WaterStation;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WaterAlertDto {
    private LocalDateTime publishDate;
    private boolean isActive;
    private DangerType waterDanger;
    private WaterStation waterStation;

    public static WaterAlertDto build(WaterAlert waterAlert) {
        return new WaterAlertDto(waterAlert.getPublishDate(), waterAlert.isActive(), waterAlert.getWaterDanger(), waterAlert.getWaterStation());
    };
}
