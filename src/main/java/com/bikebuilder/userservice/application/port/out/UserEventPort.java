package com.bikebuilder.userservice.application.port.out;

import java.util.UUID;

public interface UserEventPort {
    void publishUserDeletedEvent(UUID userId);
}
