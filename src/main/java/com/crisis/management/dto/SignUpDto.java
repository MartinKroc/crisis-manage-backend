package com.crisis.management.dto;

import lombok.Value;

@Value
public class SignUpDto {
    private String username;
    private String email;
    private String town;
    private String phone;
    private String password;
    private boolean isEmployee;
}
