package com.crisis.management.dto;

import com.crisis.management.models.DangerType;
import lombok.Value;

@Value
public class DangerTypesDto {
    private long id;
    private String description;
    private int dangerScale;

    public static DangerTypesDto build(DangerType dangerType) {
        return new DangerTypesDto(dangerType.getDangerId(), dangerType.getDescription(), dangerType.getDangerScale());
    }

}
