package com.abn.futuretxn.controller;

import com.abn.futuretxn.domain.ClientInfo;
import com.abn.futuretxn.domain.ProductInfo;
import com.abn.futuretxn.domain.TransactionSummary;
import com.abn.futuretxn.service.TxnRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ConsumerController.class)
@AutoConfigureMockMvc
class ConsumerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TxnRecordService txnRecordService;

    @Test
    public void getTxnSummaryDetailsTest() throws Exception {
        List<TransactionSummary> list = new ArrayList<>();
        ProductInfo productInfo = new ProductInfo("SGX", "FU", "NK", "20100910");
        ClientInfo clientInfo = new ClientInfo("CL", "4321", "0002", "0001");
        TransactionSummary summary = TransactionSummary.builder().netTotal(new BigDecimal(1)).clientInfo(clientInfo).productInfo(productInfo).build();
        list.add(summary);
        when(txnRecordService.getProcessedTxnRecords()).thenReturn(list);
        mockMvc.perform(get("/v1/api/future-txn-event-consumer/summary-details").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getTxnTotalAmountForProductTest() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("exchangeCode", "SGX");
        params.add("productGroupCode", "FU");
        params.add("symbol", "NK");
        when(txnRecordService.getTxnTotalAmount(Mockito.anyString(),Mockito.anyString(), Mockito.anyString())).thenReturn(new BigDecimal(2));
        mockMvc.perform(get("/v1/api/future-txn-event-consumer/product-transaction-total").params(params).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}