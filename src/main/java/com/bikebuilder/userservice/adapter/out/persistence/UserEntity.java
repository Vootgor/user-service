package com.bikebuilder.userservice.adapter.out.persistence;

import com.bikebuilder.userservice.domain.enums.Role;
import com.bikebuilder.userservice.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String name;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Instant created;

    private Instant updated;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public User toUser() {
        return new User(
            id,
            email,
            password,
            name,
            lastName,
            phoneNumber,
            role,
            created,
            updated,
            emailVerified,
            isActive
        );
    }


    public static UserEntity create(User user) {
        return new UserEntity(
            null,
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.getLastName(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getCreated() != null ? user.getCreated() : Instant.now(),
            user.getUpdated(),
            user.isEmailVerified(),
            user.isActive()
        );
    }
}
