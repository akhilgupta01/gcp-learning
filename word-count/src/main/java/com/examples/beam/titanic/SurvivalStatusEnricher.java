package com.examples.beam.titanic;

import com.examples.beam.titanic.model.Passenger;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.List;
import java.util.stream.Collectors;

class SurvivalStatusEnricher extends DoFn<List<Passenger>, List<Passenger>> {

    @ProcessElement
    public void enrich(@Element List<Passenger> passengers, ProcessContext context) {
        List<String> passengerIds = passengers.stream()
                .map(Passenger::getPassengerId)
                .collect(Collectors.toList());
        context.output(passengers.stream()
                .map(passenger -> Passenger.builder()
                        .passengerId(passenger.getPassengerId())
                        .name(passenger.getName())
                        .age(passenger.getAge())
                        .sex(passenger.getSex())
                        .survived(true)
                        .build())
                .collect(Collectors.toList()));
    }
}
