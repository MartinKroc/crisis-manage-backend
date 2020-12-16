package com.crisis.management.dto;

import lombok.Value;

@Value
public class SignUpDto {
    private String username;
    private String email;
    private String phone;
    private String password;
}
