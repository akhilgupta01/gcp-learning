package org.example.tests.spring.jobs;

import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {

    @Bean
    public ApplicationContextFactory processTitanicDataJobContextFactory(){
        return new GenericApplicationContextFactory(ProcessTitanicData.class);
    }
}
