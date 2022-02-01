package com.examples.beam.core.functions;

import com.examples.beam.titanic.tags.Tags;
import com.opencsv.CSVParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;

@Slf4j
public abstract class CsvStringParserFunction<T> extends DoFn<String, T> {

    @ProcessElement
    public void processElement(@Element String csvInputRow, ProcessContext processContext){
        CSVParser csvParser = new CSVParser();
        if(csvInputRow.startsWith(headerRow())){
            log.info("Header row found");
            return;
        }
        try {
            String[] fields = csvParser.parseLine(csvInputRow);
            processContext.output(mapFields(fields));
        }catch (Exception ex){
            processContext.output(Tags.INVALID, csvInputRow);
        }
    }

    public abstract String headerRow();

    public abstract T mapFields(String[] fields);
}
