package com.bikebuilder.userservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bikebuilder.bikebuilderexceptionstarter.InvalidEmailException;
import com.bikebuilder.bikebuilderexceptionstarter.InvalidPasswordException;
import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import com.bikebuilder.userservice.application.port.in.command.UserUpdateCommand;
import com.bikebuilder.userservice.domain.enums.Role;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private UserCreateCommand createCommand;
    private UserUpdateCommand updateCommand;

    @BeforeEach
    public void setUp() {
        createCommand = new UserCreateCommand("1@email.com", "1234567");
        updateCommand = new UserUpdateCommand(
            UUID.randomUUID(),
            "2@email.com",
            "qwerty",
            "Ivan",
            "Ivanov",
            "8-800-2000-600"
        );
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User user = User.create(createCommand);

        assertNotNull(user);
        assertNotNull(user.getCreated());
        assertEquals(createCommand.email(), user.getEmail());
        assertEquals(createCommand.password(), user.getPassword());
        assertSame(Role.ADMIN, user.getRole());
        assertTrue(user.isActive());
        assertFalse(user.isEmailVerified());
    }

    @Test
    void shouldThrowInvalidEmailException_WhenCreateWithInvalidEmail() {
        UserCreateCommand invalidCommand = new UserCreateCommand("email", "1234567");

        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class, () -> User.create(invalidCommand)
        );
        assertEquals("Введён не корректный email. Формат ввода - email@email.com", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidPasswordException_WhenCreateWithInvalidPassword() {
        UserCreateCommand invalidCommand = new UserCreateCommand("1@email.com", "123");

        InvalidPasswordException exception = assertThrows(
            InvalidPasswordException.class, () -> User.create(invalidCommand)
        );
        assertEquals("Пароль должен быть не менее 6 символов", exception.getMessage());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        User user = User.create(createCommand);
        User updatedUser = user.update(updateCommand);

        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getEmail());
        assertNotNull(updatedUser.getPassword());
        assertNotNull(updatedUser.getUpdated());

        assertEquals(updateCommand.email(), updatedUser.getEmail());
        assertEquals(updateCommand.password(), updatedUser.getPassword());
        assertEquals(updateCommand.name(), updatedUser.getName());
        assertEquals(updateCommand.lastName(), updatedUser.getLastName());
        assertEquals(updateCommand.phoneNumber(), updatedUser.getPhoneNumber());
        assertEquals(user.getCreated(), updatedUser.getCreated());
        assertEquals(user.getRole(), updatedUser.getRole());
        assertTrue(user.isActive());
        assertFalse(user.isEmailVerified());
    }

    @Test
    void shouldThrowInvalidEmailException_WhenUpdateWithInvalidEmail() {
        User user = User.create(createCommand);
        UserUpdateCommand invalidCommand = new UserUpdateCommand(
            UUID.randomUUID(),
            "2@email",
            "qwerty",
            "Ivan",
            "Ivanov",
            "8-800-2000-600"
        );

        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class, () -> user.update(invalidCommand)
        );
        assertEquals("Введён не корректный email. Формат ввода - email@email.com", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidPasswordException_WhenUpdateWithInvalidPassword() {
        User user = User.create(createCommand);
        UserUpdateCommand invalidCommand = new UserUpdateCommand(
            UUID.randomUUID(),
            "2@email.com",
            "qwer",
            "Ivan",
            "Ivanov",
            "8-800-2000-600"
        );

        InvalidPasswordException exception = assertThrows(
            InvalidPasswordException.class, () -> user.update(invalidCommand)
        );
        assertEquals("Пароль должен быть не менее 6 символов", exception.getMessage());
    }
}
