package com.bikebuilder.userservice.adapter.in.web;

import com.bikebuilder.userservice.adapter.in.CreateUserUseCase;
import com.bikebuilder.userservice.adapter.in.web.dto.UserCreateRequest;
import com.bikebuilder.userservice.adapter.in.web.dto.UserResponse;
import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public UserResponse createUser(@RequestBody @Valid UserCreateRequest request) {
        var command = new UserCreateCommand(request.email(), request.password());
        var response = createUserUseCase.createUser(command);
        return UserResponse.fromUser(response);
    }
}
