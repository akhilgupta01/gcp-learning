package com.examples.beam.titanic.tags;

import com.examples.beam.core.model.AuditableRecord;
import com.examples.beam.titanic.model.Passenger;
import org.apache.beam.sdk.values.TupleTag;

public class Tags {
    public static final TupleTag<Passenger> VALID = new TupleTag<Passenger>(){};
    public static final TupleTag<String> INVALID = new TupleTag<String>(){};
    public static final TupleTag<Passenger> ELIGIBLE = new TupleTag<Passenger>(){};
    public static final TupleTag<Passenger> INELIGIBLE = new TupleTag<Passenger>(){};
}
