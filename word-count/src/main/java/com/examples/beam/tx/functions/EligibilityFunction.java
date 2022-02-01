package com.examples.beam.tx.functions;

import com.examples.beam.core.model.EligibilityStatus;
import com.examples.beam.titanic.model.Passenger;
import com.examples.beam.tx.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;

@Slf4j
public class EligibilityFunction extends DoFn<Transaction, Transaction> {

    @ProcessElement
    public void checkEligibility(@Element Transaction transaction, ProcessContext context) {
        EligibilityStatus eligibilityStatus = null;
        if (transaction.getQuantity() > 2000){
            log.info(transaction.getTxId() + " is eligible");
            eligibilityStatus = EligibilityStatus.builder().eligible(true).reason("GT-2000").build();
        }else{
            log.info(transaction.getTxId() + " is not eligible");
            eligibilityStatus = EligibilityStatus.builder().eligible(false).reason("LT-2000").build();
        }
        transaction.setEligibilityStatus(eligibilityStatus);
        context.output(transaction);
    }
}
