package com.examples.beam.tx.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxReport {
    private String isin;
    private Integer totalQty;
    private Integer minAmount;
    private Integer maxAmount;
}
