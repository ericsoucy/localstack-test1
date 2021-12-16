package com.example.demolocalstack.sns.consumer;

import com.example.demolocalstack.sns.event.Event;
import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SimpleMessageConsumer2 implements MessageConsumer {

    @Value("${cloud.aws.sqs.order-queue-2.url}")
    public static final String ORDER_QUEUE_2 = "order-queue-2";

    @Override
    @SqsListener(value = ORDER_QUEUE_2, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consume(@NotificationMessage Event event) {
        if (event != null) {
            log.info("Received order event for consumer 2: " + event);
        }
    }
}
