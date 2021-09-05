package org.example.tests.spring.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class JobResult {
    private HttpStatus status;
    private String message;
}
