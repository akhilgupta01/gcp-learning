package com.guptakh.gcp;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
public class JobsController {

    @Value("gs://${gcs-resource-test-bucket}/all_roles.csv")
    private Resource gsFile;

    @GetMapping("/jobs/storage")
    @SneakyThrows
    public String readStorage(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(gsFile.getInputStream()));
        String line;
        int count=0;
        while((line=reader.readLine())!=null) {
            count++;
        }
        return String.valueOf(count);
    }

}
