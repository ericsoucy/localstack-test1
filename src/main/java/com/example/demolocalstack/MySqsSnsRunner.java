package com.example.demolocalstack;

import com.example.demolocalstack.sns.event.Event;
import com.example.demolocalstack.sns.event.EventData;
import com.example.demolocalstack.sns.event.EventType;
import com.example.demolocalstack.sns.producer.SimpleMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MySqsSnsRunner implements CommandLineRunner {
    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    SimpleMessageProducer simpleMessageProducer;

    @Override
    public void run(String... args) throws Exception {
        Event event = createOrderEvent();
        simpleMessageProducer.publish(event);
    }

    private Event createOrderEvent() {
        return Event.builder()
                .eventId(UUID.randomUUID().toString())
                .occurredAt(Instant.now().toString())
                .version(String.valueOf(atomicInteger.getAndIncrement()))
                .data(EventData
                        .builder()
                        .eventType(EventType.ORDER_CREATED)
                        .orderId(UUID.randomUUID().toString())
                        .owner("SampleProducer")
                        .build())
                .build();
    }
}
