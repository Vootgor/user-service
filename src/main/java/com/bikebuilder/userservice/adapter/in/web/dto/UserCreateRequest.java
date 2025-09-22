package com.bikebuilder.userservice.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
    @NotBlank String email,
    @NotEmpty String password
) {

}
