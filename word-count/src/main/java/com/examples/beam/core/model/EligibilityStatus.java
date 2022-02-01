package com.examples.beam.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EligibilityStatus {
    private boolean eligible;
    private String reason;
}
