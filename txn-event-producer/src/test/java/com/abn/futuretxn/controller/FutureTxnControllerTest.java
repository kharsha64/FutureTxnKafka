package com.abn.futuretxn.controller;

import com.abn.futuretxn.reader.TxnReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FutureTxnController.class)
@AutoConfigureMockMvc
public class FutureTxnControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TxnReader txnReader;

    @Test
    @DisplayName("FutureTxnController")
    void initiateMessageTest() throws Exception {
        doNothing().when(txnReader).readTransactionFile();
        mockMvc.perform(post("/v1/api/future-txn-event-producer").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted());

    }
}