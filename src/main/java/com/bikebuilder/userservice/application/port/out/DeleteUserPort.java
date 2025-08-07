package com.bikebuilder.userservice.application.port.out;

import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;

public interface DeleteUserPort {

    User deleteUser(UUID id);
}
