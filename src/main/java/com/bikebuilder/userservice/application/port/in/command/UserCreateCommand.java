package com.bikebuilder.userservice.application.port.in.command;

public record UserCreateCommand(
    String email,
    String password
) {

}
