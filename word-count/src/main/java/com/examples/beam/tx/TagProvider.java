package com.examples.beam.tx;

import com.examples.beam.titanic.tags.Tags;
import com.examples.beam.tx.model.Transaction;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;

import java.util.Arrays;

public class TagProvider {
    public static final TupleTag<Transaction> INGESTION_SUCCESS_TAG = new TupleTag<Transaction>(){};
    public static final TupleTag<String> PARSE_FAILURE_TAG = new TupleTag<String>(){};

    public static final TupleTagList INGESTION_FAILURE_TAGS = TupleTagList.of(Arrays.asList(PARSE_FAILURE_TAG, Tags.INVALID));
}
