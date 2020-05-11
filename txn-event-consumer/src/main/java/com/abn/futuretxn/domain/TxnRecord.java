package com.abn.futuretxn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxnRecord {
        private String clientType;
        private String clientNumber;
        private String accountNumber;
        private String subAccountNumber;
        private String exchangeCode;
        private String productGroupCode;
        private String symbol;
        private String expirationDate;
        private String quantityLong;
        private String quantityShort;
}
