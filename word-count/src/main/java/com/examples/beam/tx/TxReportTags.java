package com.examples.beam.tx;

import com.examples.beam.core.Tags;
import com.examples.beam.tx.model.Transaction;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;

public class TxReportTags {
    public static final TupleTag<Transaction> INGESTION_SUCCESS_TAG = new TupleTag<Transaction>(){};
    public static final TupleTagList INGESTION_FAILURE_TAGS = TupleTagList.of(Tags.FAILED_INGESTION);

    public static final TupleTag<Transaction> ELIGIBLE = new TupleTag<Transaction>(){};
    public static final TupleTag<Transaction> NOT_ELIGIBLE = new TupleTag<Transaction>(){};

}
