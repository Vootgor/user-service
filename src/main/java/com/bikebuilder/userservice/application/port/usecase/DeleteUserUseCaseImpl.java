package com.bikebuilder.userservice.application.port.usecase;

import com.bikebuilder.userservice.adapter.in.DeleteUserUseCase;
import com.bikebuilder.userservice.application.port.out.DeleteUserPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final DeleteUserPort deleteUserPort;

    @Override
    public User deleteUser(UUID id) {
        return deleteUserPort.deleteUser(id);
    }
}
