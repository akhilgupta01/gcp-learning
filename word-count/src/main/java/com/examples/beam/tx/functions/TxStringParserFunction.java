package com.examples.beam.tx.functions;

import com.examples.beam.core.functions.CsvStringParserFunction;
import com.examples.beam.tx.model.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TxStringParserFunction extends CsvStringParserFunction<Transaction> {

    @Override
    public String headerRow(){
        return "\"txId\", \"isin\", \"quantity\", \"customerId\"";
    }

    @Override
    public Transaction mapFields(String[] fields){
        return Transaction.builder()
                .txId(fields[0])
                .isin(fields[1])
                .quantity(Integer.valueOf(fields[2]))
                .customerId(fields[3])
                .build();
    }
}
