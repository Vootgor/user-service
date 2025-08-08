package com.bikebuilder.userservice.adapter.in;

import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;

public interface DeleteUserUseCase {
    User deleteUser(UUID id);
}
