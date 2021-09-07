package org.example.tests.spring.jobs.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import static java.lang.Boolean.FALSE;

public class PassengerMapper implements FieldSetMapper<Passenger> {
    public Passenger mapFieldSet(FieldSet fieldSet){
        return Passenger.builder()
                .passengerId(fieldSet.readString(0))
                .survived(fieldSet.readInt(1) == 1? Boolean.TRUE: FALSE)
                .pClass(fieldSet.readInt(2))
                .name(fieldSet.readString(3))
                .sex(fieldSet.readString(4))
                .age(fieldSet.readDouble(5))
                .build();
    }
}
