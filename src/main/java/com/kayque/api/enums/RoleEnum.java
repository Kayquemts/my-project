package com.kayque.api.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(1L),
    USER(2L),
    PROFESSOR(3L);

    Long roleId;

    RoleEnum(Long roleId) {
        this.roleId = roleId;
    }
}
