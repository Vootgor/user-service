package com.bikebuilder.userservice.adapter.in;

import com.bikebuilder.userservice.application.port.in.command.UserUpdateCommand;
import com.bikebuilder.userservice.domain.model.User;

public interface UpdateUserUseCase {

    User updateUser(UserUpdateCommand command);
}
