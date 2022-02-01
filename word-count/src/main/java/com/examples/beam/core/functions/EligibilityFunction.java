package com.examples.beam.core.functions;

import com.examples.beam.core.model.AuditableRecord;
import com.examples.beam.core.model.EligibilityStatus;
import com.examples.beam.tx.model.Transaction;
import org.apache.beam.sdk.transforms.DoFn;

public abstract class EligibilityFunction extends DoFn<AuditableRecord, AuditableRecord> {

    @ProcessElement
    public void checkEligibility(@Element AuditableRecord dataRecord, ProcessContext context) {
        EligibilityStatus eligibilityStatus = doEligibilityCheck(dataRecord);
        dataRecord.setEligibilityStatus(eligibilityStatus);
        context.output(dataRecord);
    }

    protected abstract EligibilityStatus doEligibilityCheck(AuditableRecord dataRecord);

}
