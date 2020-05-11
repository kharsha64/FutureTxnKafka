package com.abn.futuretxn.producer;

import com.abn.futuretxn.domain.TxnRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Random;

@Component
@Slf4j
public class FutureTxnProducer {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Autowired
    private ObjectMapper objectMapper;

    public ListenableFuture<SendResult<Integer, String>> sendTxEvents(TxnRecord record) throws JsonProcessingException {
        Random random = new Random(10000);
        Integer key = random.nextInt();
        String message = objectMapper.writeValueAsString(record);
        System.out.println("Message : " +message);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, message, topic);
        ListenableFuture<SendResult<Integer, String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                handleFailure(key, message, throwable);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, message, result);
            }
        });
        return listenableFuture;
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String message, String topic) {
        return new ProducerRecord<>(topic,null, key, message, null);
    }

    private void handleFailure(Integer key, String value, Throwable throwable) {
        log.error("Error sending message - Exception: {}", throwable.getMessage());
        try {
            throw throwable;
        } catch (Throwable e) {
            log.error("Error handleFailure() : {}", e.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message sent successfully for the key: {} and the value: {}, partition: {}", key, value, result.getRecordMetadata().partition());
    }
}
