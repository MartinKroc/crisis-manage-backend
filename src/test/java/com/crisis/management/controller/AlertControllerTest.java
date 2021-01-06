package com.crisis.management.controller;

import com.crisis.management.dto.AddAlertDto;
import com.crisis.management.dto.AlertPropositionDto;
import com.crisis.management.dto.SignUpDto;
import com.crisis.management.dto.WaterAlertDto;
import com.crisis.management.models.AlertProposition;
import com.crisis.management.models.User;
import com.crisis.management.models.WaterAlert;
import com.crisis.management.models.enums.AlertType;
import com.crisis.management.repo.AlertRepo;
import com.crisis.management.repo.UserRepo;
import com.crisis.management.repo.WaterAlertRepo;
import com.crisis.management.repo.WeatherAlertRepo;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlertControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepo userRepo;

    @Autowired
    WaterAlertRepo waterAlertRepo;

    @Autowired
    WeatherAlertRepo weatherAlertRepo;

    @Autowired
    AlertRepo alertRepo;

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
    void should_get_alerts() throws Exception {

        mockMvc.perform(get("/api/alert")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void should_post_alert() throws Exception {

        AddAlertDto addAlertDto = new AddAlertDto(AlertType.WATER,"sssddd",1,1,33.3,22.1);
        MvcResult r = mockMvc.perform(post("/api/alert")
                .header("authorization", addUser.getBearerToken())
                .content(objectMapper.writeValueAsString(addAlertDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        long ap = objectMapper.readValue(r.getResponse().getContentAsString(),long.class);
        WaterAlert a = waterAlertRepo.findById(ap).orElseThrow();

        waterAlertRepo.delete(a);
    }

    @Test
    void should_change_alert_status() throws Exception {

        AddAlertDto addAlertDto = new AddAlertDto(AlertType.WATER,"sssddd",1,1,33.3,22.1);
        MvcResult r = mockMvc.perform(post("/api/alert")
                .header("authorization", addUser.getBearerToken())
                .content(objectMapper.writeValueAsString(addAlertDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        long ap = objectMapper.readValue(r.getResponse().getContentAsString(),long.class);
        WaterAlert a = waterAlertRepo.findById(ap).orElseThrow();

        MvcResult r2 = mockMvc.perform(get("/api/alert/status/" + AlertType.WATER + "/" + a.getId())
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        long ap2 = objectMapper.readValue(r2.getResponse().getContentAsString(),long.class);
        WaterAlert a2 = waterAlertRepo.findById(ap2).orElseThrow();

        assertEquals(false, a2.isActive());

        waterAlertRepo.delete(a2);
    }

    @Test
    void should_get_suggestions() throws Exception {

        mockMvc.perform(get("/api/alert/suggestion")
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
