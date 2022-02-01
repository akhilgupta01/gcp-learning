package com.examples.beam.core;

import org.apache.beam.sdk.values.TupleTag;

public class Tags {
    public static final TupleTag<String> FAILED_INGESTION = new TupleTag<String>(){};
}
