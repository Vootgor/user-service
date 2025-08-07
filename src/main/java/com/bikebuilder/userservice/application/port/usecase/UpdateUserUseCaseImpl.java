package com.bikebuilder.userservice.application.port.usecase;

import com.bikebuilder.userservice.adapter.in.UpdateUserUseCase;
import com.bikebuilder.userservice.application.port.in.command.UserUpdateCommand;
import com.bikebuilder.userservice.application.port.out.UpdateUserPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UpdateUserPort updatePort;

    @Override
    public User updateUser(UserUpdateCommand command) {
        User user = User.update(command);
        return updatePort.update(user) ;
    }
}
