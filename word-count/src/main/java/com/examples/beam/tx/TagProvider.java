package com.examples.beam.tx;

import com.examples.beam.tx.model.Transaction;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;

import java.io.Serializable;

public class TagProvider implements Serializable {
    public static final TupleTag<Transaction> INGESTION_SUCCESS_TAG = new TupleTag<>();
    public static final TupleTag<String> PARSE_FAILURE_TAG = new TupleTag<>();

    public static final TupleTagList INGESTION_FAILURE_TAGS = TupleTagList.of(PARSE_FAILURE_TAG);
}
