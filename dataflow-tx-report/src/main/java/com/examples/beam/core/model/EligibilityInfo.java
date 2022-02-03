package com.examples.beam.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class EligibilityInfo implements DataRecord{
    private String businessId;
    private boolean eligible;
    private String reason;
}
