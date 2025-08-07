package com.bikebuilder.userservice.application.port.service;

import com.bikebuilder.userservice.adapter.in.CreateUserUseCase;
import com.bikebuilder.userservice.adapter.in.GetUserFromIdUseCase;
import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import com.bikebuilder.userservice.application.port.out.GetUserFromIdPort;
import com.bikebuilder.userservice.application.port.out.SaveUserPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements
    CreateUserUseCase,
    GetUserFromIdUseCase
{

    private final SaveUserPort saveUserPort;
    private final GetUserFromIdPort getUserFromIdPort;

    @Override
    public User createUser(UserCreateCommand command) {
        User user = User.create(command);
        return saveUserPort.save(user);
    }


    @Override
    public User getUser(UUID id) {
        return getUserFromIdPort.getUser(id);
    }
}
