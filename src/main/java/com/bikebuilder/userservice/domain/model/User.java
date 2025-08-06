package com.bikebuilder.userservice.domain.model;

import com.bikebuilder.userservice.domain.enums.Role;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private Instant created;
    private Instant updated;
    private boolean emailVerified;
    private boolean isActive;
}
