package com.examples.beam.titanic.tags;

import com.examples.beam.titanic.model.Passenger;
import lombok.Builder;
import lombok.Data;
import org.apache.beam.sdk.values.TupleTag;

@Data
@Builder
public class EligibilityTag extends TupleTag<Passenger> {

    private String reasonCode;
    private boolean eligible;
}
