package com.example.demolocalstack.sns.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventData {
    private String orderId;
    private String owner;
    private EventType eventType;
}
