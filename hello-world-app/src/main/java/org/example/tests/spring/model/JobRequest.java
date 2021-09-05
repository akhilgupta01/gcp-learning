package org.example.tests.spring.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class JobRequest {
    private String jobName;
    private Map<String, String> params;
}
