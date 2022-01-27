package com.examples.beam.titanic;

import com.examples.beam.titanic.model.Passenger;
import com.examples.beam.titanic.service.HelplineService;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SurvivalStatusEnricher extends DoFn<List<Passenger>, List<Passenger>> {
    private HelplineService helplineService;

    @Setup
    public void init() {
        this.helplineService = new HelplineService();
    }

    @ProcessElement
    public void enrich(@Element List<Passenger> passengers, ProcessContext context) {
        List<String> passengerIds = passengers.stream()
                .map(Passenger::getPassengerId)
                .collect(Collectors.toList());
        Map<String, Boolean> survivalStatusMap = helplineService.hasSurvived(passengerIds);
        context.output(passengers.stream()
                .map(passenger -> Passenger.builder()
                        .passengerId(passenger.getPassengerId())
                        .name(passenger.getName())
                        .age(passenger.getAge())
                        .sex(passenger.getSex())
                        .survived(survivalStatusMap.get(passenger.getPassengerId()))
                        .build())
                .collect(Collectors.toList()));
    }
}
