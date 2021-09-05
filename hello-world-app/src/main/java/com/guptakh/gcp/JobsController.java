package com.guptakh.gcp;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class JobsController {

    @Value("gs://${gcs-resource-test-bucket}/all_roles.csv")
    private Resource gsFile;

    @GetMapping("/jobs/storage")
    @SneakyThrows
    public String readStorage(){
        FileReader fileReader = new FileReader(this.gsFile.getFile());
        BufferedReader br=new BufferedReader(fileReader);
        String line;
        int count=0;
        while((line=br.readLine())!=null) {
            count++;
        }
        return String.valueOf(count);
    }

}
