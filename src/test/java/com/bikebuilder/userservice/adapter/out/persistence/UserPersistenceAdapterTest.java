package com.bikebuilder.userservice.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bikebuilder.bikebuilderexceptionstarter.UserNotFoundException;
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
    void save_shouldSaveUserSuccessfully() {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setEmail("1@email.com");

        when(userRepository.save(any())).thenReturn(entity);

        User result = adapter.save(user);

        verify(userRepository).save(any());
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void getUser_shouldGetUserSuccessfully() {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setEmail("1@email.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        User result = adapter.getUser(userId);

        assertEquals(userId, result.getId());
        assertEquals("1@email.com", result.getEmail());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUser_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class, () -> adapter.getUser(userId));

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void deleteUser_shouldDeleteUserSuccessfully() {
        UserEntity entity = new UserEntity();
        entity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        User result = adapter.deleteUser(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class, () -> adapter.deleteUser(userId)
        );
        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).deleteById(userId);
    }


    @Test
    void update_shouldCallFindByIdAndSave_whenUserExists() {
        UserEntity existingEntity = new UserEntity();
        existingEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingEntity));

        User result = adapter.update(user);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingEntity);
    }

    @Test
    void update_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adapter.update(user));
        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }
}