package com.abn.futuretxn.producer;

import com.abn.futuretxn.domain.TxnRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Profile("local")
public class FutureTxnProducerTest {

    @InjectMocks
    private FutureTxnProducer futureTxnProducer;

    @Mock
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Spy
    private ObjectMapper objectMapper;

    //
    @Mock
    private ListenableFuture<SendResult<Integer, String>> listenableFuture;

    @Mock
    private ProducerRecord<Integer, String> producerRecord;



    @Test
    void sendTxEventsTest_handleFailure() throws JsonProcessingException, ExecutionException, InterruptedException {
        TxnRecord txnRecord = TxnRecord.builder().clientType("CL").clientNumber("4321").subAccountNumber("0001").exchangeCode("SGX").productGroupCode("FU").symbol("NK    ").expirationDate("20100910").quantityLong("0000000001").quantityShort("0000000000").accountNumber("0002").build();
        SettableListenableFuture future = new SettableListenableFuture();

        future.setException(new RuntimeException("Exception calling Kafka"));
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);

        assertThrows(Exception.class, () -> futureTxnProducer.sendTxEvents(txnRecord).get());

    }

    @Test
    public void sendTxEventsTest() throws JsonProcessingException {

        TxnRecord txnRecord = TxnRecord.builder().clientType("CL").clientNumber("4321").subAccountNumber("0001").exchangeCode("SGX").productGroupCode("FU").symbol("NK    ").expirationDate("20100910").quantityLong("0000000001").quantityShort("0000000000").accountNumber("0002").build();

        //when(futureTxnProducer.buildProducerRecord(Mockito.any(),Mockito.anyString(),Mockito.anyString())).thenReturn(producerRecord);
        when(kafkaTemplate.send(producerRecord)).thenReturn(listenableFuture);
        futureTxnProducer.sendTxEvents(txnRecord);
        //Assertions.assert

    }

    /*@Test
    public void sendTxEventsExceptionTest() throws JsonProcessingException {

        //when(futureTxnProducer.buildProducerRecord(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString()))
        //when(kafkaTemplate.send(producerRecord)).thenReturn(listenableFuture);
        //when(kafkaTemplate.send(producerRecord)).thenThrow(JsonProcessingException.class);
        //futureTxnProducer.sendTxEvents(null);
        TxnRecord txnRecord = TxnRecord.builder().clientType("/").clientNumber("/").subAccountNumber("/").exchangeCode("SGX").productGroupCode("FU").symbol("NK    ").expirationDate("20100910").quantityLong("0000000001").quantityShort("0000000000").accountNumber("0002").build();
        when(objectMapper.writeValueAsString(txnRecord)).thenThrow(JsonProcessingException.class);
        assertThrows(JsonProcessingException.class, () -> futureTxnProducer.sendTxEvents(txnRecord));
    }*/

}