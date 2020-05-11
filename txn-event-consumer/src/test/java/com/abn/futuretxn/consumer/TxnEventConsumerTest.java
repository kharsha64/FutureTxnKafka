package com.abn.futuretxn.consumer;

import com.abn.futuretxn.domain.TxnRecord;
import com.abn.futuretxn.service.TxnRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.when;

//import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class TxnEventConsumerTest {

    @InjectMocks
    private TxnEventConsumer consumer;

    @Mock
    private ConsumerRecord<Integer, String> consumerRecord;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TxnRecordService service;

    @Test
    void onMessageConsumeTest() throws IOException {
        String message = "{\"clientType\":\"CL\",\"clientNumber\":\"4321\",\"accountNumber\":\"0002\",\"subAccountNumber\":\"0001\",\"exchangeCode\":\"SGX\",\"productGroupCode\":\"FU\",\"symbol\":\"NK    \",\"expirationDate\":\"20100910\",\"quantityLong\":\"0000000001\",\"quantityShort\":\"0000000000\"}";
        TxnRecord txnRecord = TxnRecord.builder().clientType("CL").clientNumber("4321").subAccountNumber("0001").exchangeCode("SGX").productGroupCode("FU").symbol("NK    ").expirationDate("20100910").quantityLong("0000000001").quantityShort("0000000000").accountNumber("0002").build();
        when(consumerRecord.value()).thenReturn(message);
        when(service.createFutureTxn(Mockito.any())).thenReturn(1l);
        long id = consumer.onMessageConsume(consumerRecord);
        Assertions.assertEquals(1l, 1l);
    }

}