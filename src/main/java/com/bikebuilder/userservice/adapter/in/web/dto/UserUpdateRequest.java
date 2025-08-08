package com.bikebuilder.userservice.adapter.in.web.dto;

import com.bikebuilder.userservice.application.port.in.command.UserUpdateCommand;
import java.util.UUID;

public record UserUpdateRequest(
    String email,
    String password,
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
