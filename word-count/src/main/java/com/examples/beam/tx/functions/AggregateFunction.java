package com.examples.beam.tx.functions;

import com.examples.beam.tx.model.Transaction;
import com.examples.beam.tx.model.TxReport;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.concurrent.atomic.AtomicInteger;

public class AggregateFunction extends DoFn<KV<String, Iterable<Transaction>>, TxReport> {

    @ProcessElement
    public void checkEligibility(@Element KV<String, Iterable<Transaction>> transactions, ProcessContext context) {
        AtomicInteger totalQuantity = new AtomicInteger(0);
        transactions.getValue().forEach(tx -> totalQuantity.addAndGet(tx.getQuantity()));
        TxReport report = TxReport.builder()
                .isin(transactions.getKey())
                .totalQty(totalQuantity.get())
                .build();
        context.output(report);
    }
}
