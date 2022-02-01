package com.examples.beam.tx.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class TxReport implements Serializable {
    private String isin;
    private Integer totalQty;
    private Integer minAmount;
    private Integer maxAmount;
}
