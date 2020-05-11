package com.abn.futuretxn.controller;

import com.abn.futuretxn.domain.Amount;
import com.abn.futuretxn.domain.TransactionSummary;
import com.abn.futuretxn.service.TxnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/api/future-txn-event-consumer")
public class ConsumerController {

    @Autowired
    private TxnRecordService service;

    @GetMapping("/summary-details")
    public ResponseEntity<List<TransactionSummary>> c() {
        List<TransactionSummary> transactionSummary = service.getProcessedTxnRecords();
        return new ResponseEntity<>(transactionSummary, HttpStatus.OK);
    }

    @GetMapping("/product-transaction-total")
    public ResponseEntity<Amount> getTxnTotalAmountForProduct(@RequestParam String exchangeCode, @RequestParam String productGroupCode, @RequestParam String symbol) {

        BigDecimal total = service.getTxnTotalAmount(exchangeCode, productGroupCode, symbol);
        Amount amount = new Amount(total);

        return new ResponseEntity<>(amount, HttpStatus.OK);
    }
}
