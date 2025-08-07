package com.bikebuilder.userservice.application.port.usecase;

import com.bikebuilder.userservice.adapter.in.GetUserFromIdUseCase;
import com.bikebuilder.userservice.application.port.out.GetUserFromIdPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserFromIdUseCaseImpl implements GetUserFromIdUseCase {

    private final GetUserFromIdPort getUserFromIdPort;


    @Override
    public User getUser(UUID id) {
        return getUserFromIdPort.getUser(id);
    }
}
