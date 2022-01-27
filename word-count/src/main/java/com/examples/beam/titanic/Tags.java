package com.examples.beam.titanic;

import com.examples.beam.titanic.model.Passenger;
import org.apache.beam.sdk.values.TupleTag;

public class Tags {
    public static final TupleTag<Passenger> VALID = new TupleTag<Passenger>(){};
    public static final TupleTag<String> INVALID = new TupleTag<String>(){};
}
