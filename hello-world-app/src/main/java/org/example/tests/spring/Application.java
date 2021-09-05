package org.example.tests.spring;

import org.example.tests.spring.config.LazyJobConfiguration;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.AutomaticJobRegistrar;
import org.springframework.batch.core.configuration.support.DefaultJobLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = "org.example", excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value = LazyJobConfiguration.class))
@ConfigurationPropertiesScan(basePackages = "org.example")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public AutomaticJobRegistrar registrar(List<ApplicationContextFactory> contextFactories, JobRegistry jobRegistry){
		AutomaticJobRegistrar registrar = new AutomaticJobRegistrar();
		registrar.setJobLoader(new DefaultJobLoader(jobRegistry));
		contextFactories.forEach(registrar::addApplicationContextFactory);
		return registrar;
	}
}
