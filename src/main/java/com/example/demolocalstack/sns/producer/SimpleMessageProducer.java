package com.example.demolocalstack.sns.producer;

import com.example.demolocalstack.sns.event.Event;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleMessageProducer {

    @Value("${cloud.aws.sns.order-created.arn}")
    public static final String ORDER_CREATED_TOPIC = "order-created-topic";

    @Autowired
    private NotificationMessagingTemplate notificationMessagingTemplate;

    public void publish(Event event) {
        notificationMessagingTemplate.convertAndSend(ORDER_CREATED_TOPIC, event);
    }
}
