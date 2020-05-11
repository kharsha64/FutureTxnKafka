package com.abn.futuretxn.consumer;

import com.abn.futuretxn.domain.TxnRecord;
import com.abn.futuretxn.service.TxnRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TxnEventConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TxnRecordService service;

    @KafkaListener(topics = "${spring.kafka.template.topic}")
    public Long onMessageConsume(ConsumerRecord<Integer, String> consumerRecord) throws IOException {
        log.info("Message Received : {}", consumerRecord);
        String message = consumerRecord.value();
        TxnRecord record = objectMapper.readValue(message, TxnRecord.class);
        Long id = service.createFutureTxn(record);
        return id;
    }
}
