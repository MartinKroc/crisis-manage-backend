package com.crisis.management.services;

import com.crisis.management.dto.*;
import com.crisis.management.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {

    String test(String username);

    ResponseEntity<String> createUser(SignUpDto signUpDto);

    ResponseEntity loginUser(SignInDto signInDto);

    ResponseEntity deleteUser(String username);

    UserDto getUser(Authentication authentication);

    EmployeeDto createEmployee(User user, EmployeeDto employeeDto);

    UserDto deleteEmployee(User user);

    ResponseEntity<String> changeSettings(SettingsDto settingsDto, String username);
}
