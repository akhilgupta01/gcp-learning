package com.examples.beam.tx.model;

import com.examples.beam.core.model.AbstractAuditableRecord;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction extends AbstractAuditableRecord {
    private String txId;
    private String isin;
    private Integer quantity;
    private String customerId;
}
