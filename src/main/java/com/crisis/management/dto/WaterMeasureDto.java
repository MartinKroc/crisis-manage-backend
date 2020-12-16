package com.crisis.management.dto;

import com.crisis.management.models.WaterMeasure;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WaterMeasureDto {
    private int waterLevel;
    private LocalDateTime date;

    public static WaterMeasureDto build(WaterMeasure waterMeasure) {
        return new WaterMeasureDto(waterMeasure.getWaterLevel(),waterMeasure.getDate());
    };
}
