package com.abn.futuretxn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@Profile("local")
public class EventConsumerConfig {
}
