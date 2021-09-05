package org.example.tests.spring.jobs.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class PassengerMapper implements FieldSetMapper<Passenger> {
    public Passenger mapFieldSet(FieldSet fieldSet){
        return Passenger.builder().build();
    }
}
