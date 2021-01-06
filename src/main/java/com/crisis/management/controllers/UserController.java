package com.crisis.management.controllers;

import com.crisis.management.dto.SettingsDto;
import com.crisis.management.dto.SignInDto;
import com.crisis.management.dto.SignUpDto;
import com.crisis.management.dto.UserDto;
import com.crisis.management.services.AuthorizationService;
import com.crisis.management.services.MailService;
import com.crisis.management.services.UserService;
import com.crisis.management.utilities.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final MailService mailService;

    @PostMapping("signup")
    public ResponseEntity<String> signupUser(@RequestBody SignUpDto signupDto) {
        return authorizationService.createUser(signupDto);
    }

    @PostMapping("signin")
    public ResponseEntity signinUser(@RequestBody SignInDto signinDto) {
        return authorizationService.loginUser(signinDto);
    }

    @GetMapping("users")
    public UserDto getUser(Authentication authentication) {
        return authorizationService.getUser(authentication);
    }

    @PostMapping("/settings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changeUserSettings(@RequestBody SettingsDto settingsDto, Authentication authentication) {
        return authorizationService.changeSettings(settingsDto, AuthMiner.getUsername(authentication));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
