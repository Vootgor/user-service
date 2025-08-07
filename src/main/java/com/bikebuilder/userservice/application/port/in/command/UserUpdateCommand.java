package com.bikebuilder.userservice.application.port.in.command;

import java.util.UUID;

public record UserUpdateCommand(
    UUID id,
    String email,
    String password,
    String name,
    String lastName,
    String phoneNumber
) {

}
