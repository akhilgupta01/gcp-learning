package com.examples.beam.tx.model;

import com.examples.beam.core.model.DataRecord;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction implements DataRecord {
    private String txId;
    private String isin;
    private Integer quantity;
    private String customerId;

    @Override
    public String getBusinessId() {
        return txId;
    }
}
