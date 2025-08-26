package com.bikebuilder.userservice.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bikebuilder.userservice.domain.enums.Role;
import com.bikebuilder.userservice.domain.model.User;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserPersistenceAdapterTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserPersistenceAdapter adapter;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User(
            userId,
            "1@email.com",
            "password",
            "Ivan",
            "Ivanov",
            "+78002000600",
            Role.ADMIN, Instant.now(),
            null,
            false,
            true
        );
    }

    @Test
    void shouldCallFindByIdAndSave_WhenUserExists() {
        UserEntity existingEntity = new UserEntity();
        existingEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingEntity));

        adapter.update(user);
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingEntity);
    }

    @Test
    void shouldThrowIllegalArgumentException_WhenUserDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> adapter.update(user));
        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }
}