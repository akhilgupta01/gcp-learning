package com.examples.beam.core.model;

import lombok.Data;

@Data
public abstract class AbstractAuditableRecord implements AuditableRecord{
    private EligibilityStatus eligibilityStatus;
}
