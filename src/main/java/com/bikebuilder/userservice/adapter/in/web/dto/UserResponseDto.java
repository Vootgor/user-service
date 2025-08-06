package com.bikebuilder.userservice.adapter.in.web.dto;

import com.bikebuilder.userservice.domain.enums.Role;
import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
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

}
