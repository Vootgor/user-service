package com.bikebuilder.userservice.adapter.out.persistence;

import com.bikebuilder.userservice.adapter.out.persistence.mapper.UserMapper;
import com.bikebuilder.userservice.application.port.out.SaveUserPort;
import com.bikebuilder.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved  = userRepository.save(entity);
        return userMapper.toDomain(saved);
    }
}
