package com.examples.beam.titanic.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Passenger implements Serializable {
    private String passengerId;
    private String name;
    private String sex;
    private String age;
    private Boolean survived;
}
