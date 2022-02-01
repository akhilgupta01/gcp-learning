package com.examples.beam.core.functions;

import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.transforms.DoFn;

public class MetricFn extends DoFn<String, String> {

    public static <T> DoFn<T, T> of(String namespace, String counterName){
        return new DoFn<T, T>(){
            private final Counter counter = Metrics.counter( namespace, counterName);
            @ProcessElement
            public void processElement(ProcessContext context) {
                counter.inc();
                context.output(context.element());
            }
        };
    }
}
