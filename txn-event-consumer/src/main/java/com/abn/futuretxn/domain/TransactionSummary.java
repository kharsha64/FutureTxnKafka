package com.abn.futuretxn.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionSummary {
    private ProductInfo productInfo;
    private ClientInfo clientInfo;
    private BigDecimal netTotal;
}





