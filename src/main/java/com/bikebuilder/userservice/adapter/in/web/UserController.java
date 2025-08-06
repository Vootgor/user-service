package com.bikebuilder.userservice.adapter.in.web;

import com.bikebuilder.userservice.adapter.in.CreateUserUseCase;
import com.bikebuilder.userservice.adapter.in.web.dto.UserCreateRequestDto;
import com.bikebuilder.userservice.adapter.in.web.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public UserResponseDto createUser(@RequestBody @Valid UserCreateRequestDto requestDto ){

        UserResponseDto response = createUserUseCase.createUser(requestDto);
        return response;
    }
}
