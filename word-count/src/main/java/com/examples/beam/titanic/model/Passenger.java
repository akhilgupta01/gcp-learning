package com.examples.beam.titanic.model;

import com.examples.beam.core.model.AuditableRecord;
import com.examples.beam.core.model.EligibilityStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Passenger implements AuditableRecord {
    private String passengerId;
    private String name;
    private String sex;
    private Integer age;
    private Boolean survived;

    private EligibilityStatus eligibilityStatus;
}
