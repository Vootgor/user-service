package com.bikebuilder.userservice.domain.model;

import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import com.bikebuilder.userservice.domain.enums.Role;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
@Builder
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

    public static User create(UserCreateCommand command) {
        return User.builder()
            .email(command.email())
            .password(command.password())
            .name(null)
            .lastName(null)
            .phoneNumber(null)
            .role(Role.ADMIN)
            .created(Instant.now())
            .updated(null)
            .emailVerified(false)
            .isActive(true)
            .build();
    }
}
