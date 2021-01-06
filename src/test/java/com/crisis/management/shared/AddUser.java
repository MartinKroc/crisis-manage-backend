package com.crisis.management.shared;

import com.crisis.management.dto.SignInDto;
import com.crisis.management.dto.SignUpDto;
import com.crisis.management.services.AuthorizationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Data
public class AddUser {

    private AuthorizationService authorizationService;

    private MockMvc mockMvc;

    public AddUser(AuthorizationService authorizationService, MockMvc mockMvc) {
        this.authorizationService = authorizationService;
        this.mockMvc = mockMvc;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private String bearerToken;

    SignUpDto signUpDto;

    public void createUser() throws Exception {
        signUpDto = new SignUpDto("testuser", "tester@gmail.com", "123123123", "test123");
        try {
            authorizationService.deleteUser(signUpDto.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        mockMvc.perform(post("/api/user/signup")
                .content(objectMapper.writeValueAsString(signUpDto))
                .contentType("application/json"));
    }

    public void loginUser() throws Exception {
        SignInDto signInDto = new SignInDto(signUpDto.getUsername(), signUpDto.getPassword());

        MvcResult result = mockMvc.perform(post("/api/user/signin")
                .content(objectMapper.writeValueAsString(signInDto))
                .contentType("application/json")).andReturn();
        String jwtToken = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(jwtToken);
        assertNotNull(json);
        String badToken = json.get("token").toString();
        String token = badToken.substring(1, badToken.length() - 1);
        bearerToken = new StringBuilder().append("Bearer ").append(token).toString();
    }

    public void deleteUser() {
        authorizationService.deleteUser(signUpDto.getUsername());
    }
}
