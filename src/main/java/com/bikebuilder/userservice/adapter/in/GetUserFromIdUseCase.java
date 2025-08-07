package com.bikebuilder.userservice.adapter.in;

import com.bikebuilder.userservice.domain.model.User;
import java.util.UUID;

public interface GetUserFromIdUseCase {

    User getUser(UUID uuid);
}
