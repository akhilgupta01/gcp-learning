package org.example.tests.spring.jobs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Passenger {
    private String passengerId;
    private boolean survived;
    private int pClass;
    private String name;
    private String sex;
    private double age;
}
