package com.bikebuilder.userservice.adapter.in;

import com.bikebuilder.userservice.adapter.in.web.dto.UserCreateRequestDto;
import com.bikebuilder.userservice.adapter.in.web.dto.UserResponseDto;

public interface CreateUserUseCase {

    UserResponseDto createUser(UserCreateRequestDto requestDto);
}
