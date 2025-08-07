package com.bikebuilder.userservice.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 6) String password
) {

}
