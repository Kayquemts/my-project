package com.kayque.api.entities.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    public String userName;
    public String password;
}
