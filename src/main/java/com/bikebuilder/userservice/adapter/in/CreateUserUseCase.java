package com.bikebuilder.userservice.adapter.in;

import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import com.bikebuilder.userservice.domain.model.User;

public interface CreateUserUseCase {

    User createUser(UserCreateCommand command);
}
