package com.examples.beam.titanic.functions;

import com.examples.beam.core.model.EligibilityStatus;
import com.examples.beam.titanic.model.Passenger;
import org.apache.beam.sdk.transforms.DoFn;

public class EligibilityFunction extends DoFn<Passenger, Passenger> {

    @ProcessElement
    public void checkEligibility(@Element Passenger passenger, ProcessContext context) {
        EligibilityStatus eligibilityStatus = null;
        if (passenger.getAge() > 10){
            eligibilityStatus = EligibilityStatus.builder().eligible(true).reason("REASON-X").build();
        }else{
            eligibilityStatus = EligibilityStatus.builder().eligible(false).reason("REASON-Y").build();
        }
        passenger.setEligibilityStatus(eligibilityStatus);
        context.output(passenger);
    }
}
