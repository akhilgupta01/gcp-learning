package com.examples.beam.core.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EligibilityStatus implements Serializable {
    private boolean eligible;
    private String reason;
}
