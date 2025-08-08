package com.bikebuilder.userservice.adapter.in.web.dto;

import com.bikebuilder.userservice.application.port.in.command.UserUpdateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UserUpdateRequest(
    @Email @NotBlank String email,
    @NotBlank String password,
    String name,
    String lastName,
    String phoneNumber
) {
    public static UserUpdateCommand toCommand(UUID id, UserUpdateRequest request) {
        return new UserUpdateCommand(
            id,
            request.email,
            request.password,
            request.name,
            request.lastName,
            request.phoneNumber
        );
    }

}
