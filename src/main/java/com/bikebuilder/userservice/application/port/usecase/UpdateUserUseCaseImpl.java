package com.bikebuilder.userservice.application.port.usecase;

import com.bikebuilder.userservice.adapter.in.UpdateUserUseCase;
import com.bikebuilder.userservice.application.port.in.command.UserUpdateCommand;
import com.bikebuilder.userservice.application.port.out.GetUserFromIdPort;
import com.bikebuilder.userservice.application.port.out.UpdateUserPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final GetUserFromIdPort getUser;
    private final UpdateUserPort updatePort;

    @Override
    public User updateUser(UserUpdateCommand command) {
        User user = getUser.getUser(command.id());
        User updated = user.update(command);
        return updatePort.update(updated) ;
    }
}
