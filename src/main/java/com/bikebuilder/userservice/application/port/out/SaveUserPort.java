package com.bikebuilder.userservice.application.port.out;

import com.bikebuilder.userservice.domain.model.User;

public interface SaveUserPort {

    User save(User user);
}
