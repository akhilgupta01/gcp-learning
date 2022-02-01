package com.examples.beam.titanic.functions;

import com.examples.beam.titanic.tags.Tags;
import com.examples.beam.titanic.model.Passenger;
import com.opencsv.CSVParser;
import org.apache.beam.sdk.transforms.DoFn;

public class CsvStringParserFunction extends DoFn<String, Passenger> {

    @ProcessElement
    public void processElement(@Element String csvInputRow, ProcessContext processContext){
        if(csvInputRow.startsWith("PassengerId")){
            System.out.println("Header row found");
            return;
        }
        CSVParser parser = new CSVParser();
        try {
            String[] fields = parser.parseLine(csvInputRow);
            Passenger passenger = Passenger.builder()
                    .passengerId(fields[0])
                    .name(fields[3])
                    .sex(fields[4])
                    .age(Integer.valueOf(fields[5]))
                    .build();
            processContext.output(passenger);
        }catch (Exception ex){
            processContext.output(Tags.INVALID, csvInputRow);
        }
    }
}
