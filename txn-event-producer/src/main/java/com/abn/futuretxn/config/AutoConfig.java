package com.abn.futuretxn.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("local")
public class AutoConfig {

    @Bean
    public NewTopic futureTxnEvents() {
        return TopicBuilder.name("test-future-txn-events").partitions(2).replicas(1).build();
    }
}
