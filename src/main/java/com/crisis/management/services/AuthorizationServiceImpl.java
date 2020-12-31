package com.crisis.management.services;

import com.crisis.management.configuration.jwt.JwtProvider;
import com.crisis.management.dto.*;
import com.crisis.management.models.User;
import com.crisis.management.models.UserPrincipal;
import com.crisis.management.models.authority.Role;
import com.crisis.management.models.authority.RoleName;
import com.crisis.management.repo.RoleRepo;
import com.crisis.management.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepository;
    private final JwtProvider provider;
    private final AuthenticationManager manager;

    @Override
    public String test(String username) {
        return "oke";
    }

    @Override
    public ResponseEntity<String> createUser(SignUpDto signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity.badRequest().body("Użytkownik " + signUpDto.getUsername() + " już istnieje");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository
                .findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rola nie istnieje")));

        User user = new User(signUpDto.getUsername(),
                passwordEncoder.encode(signUpDto.getPassword()),
                signUpDto.getEmail(),
                signUpDto.getPhone(),
                roles);

        userRepository.save(user);

        return ResponseEntity.ok("Użytkownik pomyślnie został zarejestrowany");
    }

    @Override
    public ResponseEntity loginUser(SignInDto signInDto) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getUsername(),
                        signInDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.generateToken(authentication);

        return ResponseEntity.ok(new JwtTokenDto(token));
    }

    @Override
    public ResponseEntity deleteUser(String username) {
        return null;
    }

    @Override
    public UserDto getUser(Authentication authentication) {
        return UserDto.build((UserPrincipal) authentication.getPrincipal());
    }

    @Override
    public EmployeeDto createEmployee(User user, EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public UserDto deleteEmployee(User user) {
        return null;
    }

    @Override
    public ResponseEntity<String> changeSettings(SettingsDto settingsDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("użytkownik nie znaleziony"));
        user.setEmail(settingsDto.getEmail());
        user.setTel(settingsDto.getPhone());
        userRepository.save(user);
        return ResponseEntity.ok("zmieniono dane");
    }
}
