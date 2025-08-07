package com.bikebuilder.userservice.adapter.in.web;

import com.bikebuilder.userservice.adapter.in.CreateUserUseCase;
import com.bikebuilder.userservice.adapter.in.DeleteUserUseCase;
import com.bikebuilder.userservice.adapter.in.GetUserFromIdUseCase;
import com.bikebuilder.userservice.adapter.in.web.dto.UserCreateRequest;
import com.bikebuilder.userservice.adapter.in.web.dto.UserResponse;
import com.bikebuilder.userservice.application.port.in.command.UserCreateCommand;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final GetUserFromIdUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public UserResponse createUser(@RequestBody @Valid UserCreateRequest request) {
        var command = new UserCreateCommand(request.email(), request.password());
        var response = createUserUseCase.createUser(command);
        return UserResponse.fromUser(response);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id){
        var response = getUserUseCase.getUser(id);
        return UserResponse.fromUser(response);
    }

    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable UUID id){
        var response = deleteUserUseCase.deleteUser(id) ;
        return UserResponse.fromUser(response);
    }

}
