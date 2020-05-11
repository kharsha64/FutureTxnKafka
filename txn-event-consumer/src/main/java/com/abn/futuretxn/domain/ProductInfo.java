package com.abn.futuretxn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
    //EXCHANGE CODE, PRODUCT GROUP CODE, SYMBOL, EXPIRATION DATE
    private String exchangeCode;
    private String productGroupCode;
    private String symbol;
    private String expDate;
}
