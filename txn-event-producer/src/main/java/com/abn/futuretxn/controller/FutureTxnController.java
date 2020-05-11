package com.abn.futuretxn.controller;

import com.abn.futuretxn.reader.TxnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/future-txn-event-producer")
public class FutureTxnController {

    @Autowired
    private TxnReader reader;

    @PostMapping
    public ResponseEntity<String> initiateMessage() throws IOException {
        reader.readTransactionFile();
        return new ResponseEntity<>("Input file processed", HttpStatus.ACCEPTED);
    }
}
