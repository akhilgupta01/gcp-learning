package org.example.tests.spring.jobs;

import com.google.cloud.storage.Storage;
import org.example.tests.spring.config.AppConfig;
import org.example.tests.spring.config.LazyJobConfiguration;
import org.example.tests.spring.jobs.model.Passenger;
import org.example.tests.spring.jobs.model.PassengerMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessTitanicData implements LazyJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Storage storage;

    @Bean
    public Job job(){
        return this.jobBuilderFactory.get("processTitanicData")
                .start(processPassengers())
                .build();
    }

    private Step processPassengers() {
        return this.stepBuilderFactory.get("loadPassengers")
                .<Passenger, Passenger>chunk(100)
                .reader(passengerReader())
                .processor(passengerEnricher())
                .writer(passengerWriter())
                .build();
    }

    private ItemWriter<Passenger> passengerWriter() {
        return new FlatFileItemWriterBuilder<Passenger>()
                .name("passengerWriter")
                .resource(new GoogleStorageResource(storage, "gs://" + appConfig.getStorageBucket() + "/passenger_out.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    private ItemProcessor<Passenger, Passenger> passengerEnricher() {
        return passenger -> passenger;
    }

    private ItemReader<Passenger> passengerReader() {
        FlatFileItemReader<Passenger> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new GoogleStorageResource(storage, "gs://" + appConfig.getStorageBucket() + "/passengers.csv"));
        itemReader.setLineMapper(passengerLineMapper());
        return itemReader;
    }

    private LineMapper<Passenger> passengerLineMapper() {
        DefaultLineMapper<Passenger> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new PassengerMapper());
        return lineMapper;
    }
}
