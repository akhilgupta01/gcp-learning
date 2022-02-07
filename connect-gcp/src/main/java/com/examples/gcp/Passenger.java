package com.examples.gcp;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Passenger {
    private String passengerId;
    private String name;
    private boolean survived;
    private String embarked;
}
