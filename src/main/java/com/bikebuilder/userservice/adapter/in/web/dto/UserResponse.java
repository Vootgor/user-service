package com.bikebuilder.userservice.adapter.in.web.dto;

import com.bikebuilder.userservice.domain.enums.Role;
import com.bikebuilder.userservice.domain.model.User;
import java.time.Instant;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String email,
    String name,
    String lastName,
    String phoneNumber,
    Role role,
    Instant created,
    boolean emailVerified,
    boolean isActive
) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getLastName(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getCreated(),
            user.isEmailVerified(),
            user.isActive()
        );
    }
}
