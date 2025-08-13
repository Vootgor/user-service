package com.bikebuilder.userservice.adapter.out.messaging;

import com.bikebuilder.userservice.application.port.out.UserEventPort;
import com.bikebuilder.userservice.adapter.in.web.dto.UserDeletedEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher implements UserEventPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "user-deleted";

    @Override
    public void publishUserDeletedEvent(UUID userId) {
        UserDeletedEvent event = new UserDeletedEvent(userId);
        kafkaTemplate.send(TOPIC, event);
    }
}
