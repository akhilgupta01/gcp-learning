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

    @GetMapping("/jobs/{jobName}")
    public String runJob(@PathVariable String jobName){
        System.out.println("Starting job " + jobName);
        final List<Integer> primeNumbers = new ArrayList<>();
        for(int i=2; i<100000; i++){
            if(i%2 == 0){
                continue;
            }
            final int num = i;
            if(primeNumbers.stream().noneMatch(x -> num % x == 0)){
                primeNumbers.add(i);
            }
        }
        return String.valueOf(primeNumbers);
    }

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
