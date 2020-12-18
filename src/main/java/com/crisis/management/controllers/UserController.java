package com.crisis.management.controllers;

import com.crisis.management.dto.SignInDto;
import com.crisis.management.dto.SignUpDto;
import com.crisis.management.models.WaterM;
import com.crisis.management.services.AuthorizationService;
import com.crisis.management.services.MailService;
import com.crisis.management.services.UserService;
import com.crisis.management.utilities.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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

    @GetMapping("test")
    @PreAuthorize("hasRole('USER')")
    public String test(Authentication authentication) {
        return authorizationService.test(AuthMiner.getUsername(authentication));
    }

    @GetMapping("test2")
    public ResponseEntity<List<WaterM>> test() {
        String uri = "https://danepubliczne.imgw.pl/api/data/hydro/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<WaterM>> response = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<WaterM>>() {
        });
        System.out.println(response);
        return response;
    }

    @GetMapping("/send")
    public String sendMail() throws MessagingException {
        mailService.sendMail("marcin1303@poczta.onet.pl",
                "Wygrałeś",
                "<b>1000 000 zł</b><br>:P", true);
        return "wysłano";
    }


}
