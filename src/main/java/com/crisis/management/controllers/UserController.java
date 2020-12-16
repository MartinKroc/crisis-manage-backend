package com.crisis.management.controllers;

import com.crisis.management.dto.SignInDto;
import com.crisis.management.dto.SignUpDto;
import com.crisis.management.services.AuthorizationService;
import com.crisis.management.services.UserService;
import com.crisis.management.utilities.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @PostMapping("signup")
    public ResponseEntity<String> signupUser(@RequestBody SignUpDto signupDto) {
        return authorizationService.createUser(signupDto);
    }

    @PostMapping("signin")
    public ResponseEntity signinUser(@RequestBody SignInDto signinDto) {
        return authorizationService.loginUser(signinDto);
    }

    @GetMapping("test")
    @PreAuthorize("hasRole('USER')")
    public String test(Authentication authentication) {
        return authorizationService.test(AuthMiner.getUsername(authentication));
    }

}
