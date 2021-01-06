package com.crisis.management.controller;

import com.crisis.management.dto.SignUpDto;
import com.crisis.management.models.User;
import com.crisis.management.repo.UserRepo;
import com.crisis.management.services.AuthorizationService;
import com.crisis.management.shared.AddUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MeasureControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepo userRepo;

    SignUpDto signUpDto;

    String token;

    User user;

    private AddUser addUser;

    @BeforeAll()
    void createUser() throws Exception {
        addUser = new AddUser(authorizationService, mockMvc);
        addUser.createUser();
        addUser.loginUser();
        user = userRepo.findByUsername("testuser").orElseThrow(() -> new Exception());
        authorizationService.createEmployee(user);
    }

    @Test
    void should_get_water_measures() throws Exception {

        mockMvc.perform(get("/api/measure/water")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void should_get_water_measures_by_water_station_id() throws Exception {

        mockMvc.perform(get("/api/measure/water/1")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void should_get_weather_measures_by_water_station_id() throws Exception {

        mockMvc.perform(get("/api/measure/weather/1")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void should_generate_random_measures() throws Exception {

        mockMvc.perform(get("/api/measure/generate")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @AfterAll()
    void deleteUser() {
        authorizationService.deleteEmployee(user);
        addUser.deleteUser();
    }
}
