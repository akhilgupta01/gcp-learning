package com.examples.beam.tx.functions;

import com.examples.beam.tx.model.Transaction;
import com.examples.beam.tx.model.TxReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class EnrichFunction extends DoFn<KV<Integer, Iterable<Transaction>>, Iterable<Transaction>> {

    @ProcessElement
    public void doAggregation(@Element KV<Integer, Iterable<Transaction>> transactions, OutputReceiver<Iterable<Transaction>> outputReceiver) {
        outputReceiver.output(transactions.getValue());
    }
}
