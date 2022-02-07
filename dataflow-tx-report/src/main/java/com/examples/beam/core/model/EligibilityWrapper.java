package com.examples.beam.core.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EligibilityWrapper implements Serializable {
    private DataRecord dataRecord;
    private boolean eligible;
    private String reason;
}
