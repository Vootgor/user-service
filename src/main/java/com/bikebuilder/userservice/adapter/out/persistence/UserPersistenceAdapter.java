package com.bikebuilder.userservice.adapter.out.persistence;

import com.bikebuilder.bikebuilderexceptionstarter.UserNotFoundException;
import com.bikebuilder.userservice.application.port.out.DeleteUserPort;
import com.bikebuilder.userservice.application.port.out.GetUserFromIdPort;
import com.bikebuilder.userservice.application.port.out.SaveUserPort;
import com.bikebuilder.userservice.application.port.out.UpdateUserPort;
import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements
    SaveUserPort,
    GetUserFromIdPort,
    DeleteUserPort,
    UpdateUserPort {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.create(user);
        UserEntity saved = userRepository.save(entity);
        return saved.toUser();
    }


    @Override
    public User getUser(UUID id) {
        UserEntity entity = userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
        return entity.toUser();
    }


    @Override
    public User deleteUser(UUID id) {
        UserEntity entity = userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return entity.toUser();
    }

    @Override
    public User update(User user) {
        UserEntity existingEntity = userRepository.findById(user.getId())
            .orElseThrow(UserNotFoundException::new);
        existingEntity.update(user);
        userRepository.save(existingEntity);
        return existingEntity.toUser();
    }
}
