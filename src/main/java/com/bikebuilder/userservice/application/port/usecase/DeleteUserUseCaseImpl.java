package com.bikebuilder.userservice.application.port.usecase;

import com.bikebuilder.userservice.adapter.in.DeleteUserUseCase;
import com.bikebuilder.userservice.application.port.out.DeleteUserPort;
import com.bikebuilder.userservice.application.port.out.UserEventPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final DeleteUserPort deleteUserPort;
    private final UserEventPort userEventPort;

    @Transactional
    @Override
    public User deleteUser(UUID id) {
        User deletedUser = deleteUserPort.deleteUser(id);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                userEventPort.publishUserDeletedEvent(deletedUser.getId());
            }
        });
        return deletedUser;
    }
}
