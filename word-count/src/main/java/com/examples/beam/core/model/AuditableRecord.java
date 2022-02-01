package com.examples.beam.core.model;

import java.io.Serializable;

public interface AuditableRecord extends Serializable {
    EligibilityStatus getEligibilityStatus();
    void setEligibilityStatus(EligibilityStatus eligibilityStatus);
}
