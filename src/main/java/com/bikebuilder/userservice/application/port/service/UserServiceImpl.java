package com.bikebuilder.userservice.application.port.service;

import com.bikebuilder.userservice.adapter.in.CreateUserUseCase;
import com.bikebuilder.userservice.adapter.in.web.dto.UserCreateRequestDto;
import com.bikebuilder.userservice.adapter.in.web.dto.UserResponseDto;
import com.bikebuilder.userservice.application.port.out.SaveUserPort;
import com.bikebuilder.userservice.domain.enums.Role;
import com.bikebuilder.userservice.domain.model.User;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CreateUserUseCase {

    private final SaveUserPort saveUserPort;

    @Override
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {

        User user = User.builder()
            .email(requestDto.email())
            .password(requestDto.password())
            .name(null)
            .lastName(null)
            .phoneNumber(null)
            .role(Role.ADMIN)
            .created(Instant.now())
            .updated(null)
            .emailVerified(false)
            .isActive(true)
            .build();

        User saved = saveUserPort.save(user);
        return new UserResponseDto(
            saved.getId(),
            saved.getEmail(),
            saved.getName(),
            saved.getLastName(),
            saved.getPhoneNumber(),
            saved.getRole(),
            saved.getCreated(),
            saved.isEmailVerified(),
            saved.isActive());
    }
}
