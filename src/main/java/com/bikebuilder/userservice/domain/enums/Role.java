package com.bikebuilder.userservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Предоставляет роль для пользователя
 *
 * @author Victor Vityushov
 */
@Getter
@AllArgsConstructor
public enum Role {
    CUSTOMER("Покупатель"),
    EMPLOYEE("Работник"),
    ADMIN("Админ");

    private final String description;
}
