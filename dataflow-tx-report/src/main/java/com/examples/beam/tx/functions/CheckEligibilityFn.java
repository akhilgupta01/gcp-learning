package com.examples.beam.tx.functions;

import com.examples.beam.core.model.EligibilityWrapper;
import com.examples.beam.tx.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;

@Slf4j
public class CheckEligibilityFn extends DoFn<Transaction, EligibilityWrapper> {

    @ProcessElement
    public void checkEligibility(@Element Transaction transaction, OutputReceiver<EligibilityWrapper> outputReceiver) {
        if (transaction.getQuantity() > 2000) {
            EligibilityWrapper eligibilityWrapper = EligibilityWrapper.builder()
                    .dataRecord(transaction)
                    .eligible(true)
                    .reason("GT-2000").build();
            outputReceiver.output(eligibilityWrapper);
        } else {
            EligibilityWrapper eligibilityWrapper = EligibilityWrapper.builder()
                    .dataRecord(transaction)
                    .eligible(false)
                    .reason("LT-2000").build();
            outputReceiver.output(eligibilityWrapper);
        }
    }
}
