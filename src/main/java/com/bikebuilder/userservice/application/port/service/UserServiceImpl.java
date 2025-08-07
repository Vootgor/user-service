package com.bikebuilder.userservice.application.port.service;

import com.bikebuilder.userservice.adapter.in.CreateUserUseCase;
import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import com.bikebuilder.userservice.application.port.out.SaveUserPort;
import com.bikebuilder.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CreateUserUseCase {

    private final SaveUserPort saveUserPort;

    @Override
    public User createUser(UserCreateCommand command) {
        User user = User.create(command);
        return saveUserPort.save(user);
    }
}
