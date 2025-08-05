package com.bikebuilder.userservice.domain.model;

import com.bikebuilder.userservice.domain.enums.Role;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
@Builder(toBuilder = true)
@AllArgsConstructor
public final class User {
    private final UUID id;
    private final String email;
    private final String password;
    private final String name;
    private final String lastName;
    private final String phoneNumber;
    private final Role role;
    private final Instant created;
    private final Instant updated;
    private final boolean emailVerified;
    private final boolean isActive;
}
