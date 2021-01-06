package com.crisis.management.controller;

import com.crisis.management.dto.AddAlertPropositionDto;
import com.crisis.management.dto.AlertPropositionDto;
import com.crisis.management.dto.SignUpDto;
import com.crisis.management.models.AlertProposition;
import com.crisis.management.models.User;
import com.crisis.management.repo.AlertPrepositionRepo;
import com.crisis.management.repo.UserRepo;
import com.crisis.management.services.AlertPropositionService;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlertPropositionTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    AlertPropositionService alertPropositionService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AlertPrepositionRepo alertPrepositionRepo;

    SignUpDto signUpDto;

    String token;

    User user;
    AlertProposition alertProposition;

    AlertPropositionDto alertPropositionDto;
    AddAlertPropositionDto addAlertPropositionDto;

    private AddUser addUser;
    long Aid;

    @BeforeAll()
    void createUser() throws Exception {
        addUser = new AddUser(authorizationService, mockMvc);
        addUser.createUser();
        addUser.loginUser();
        user = userRepo.findByUsername("testuser").orElseThrow(() -> new Exception());
        authorizationService.createEmployee(user);

        addAlertPropositionDto = new AddAlertPropositionDto(33.44,22.11,"bbbbb", 1);
    }

    @Test
    void should_get_alert_propositions() throws Exception {

        mockMvc.perform(get("/api/proposition/dangers")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void should_post_alert_proposition() throws Exception {

        MvcResult r = mockMvc.perform(post("/api/proposition")
                .header("authorization", addUser.getBearerToken())
                .content(objectMapper.writeValueAsString(addAlertPropositionDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        AlertPropositionDto ap = objectMapper.readValue(r.getResponse().getContentAsString(), AlertPropositionDto.class);
        AlertProposition a = alertPrepositionRepo.findById(ap.getAlertId()).orElseThrow();
        Aid = a.getAlertId();
    }

    @Test
    void should_change_alert_proposition_status() throws Exception {

        AddAlertPropositionDto al = new AddAlertPropositionDto(33.44,22.11,"bbbbbaaa", 1);
        MvcResult r = mockMvc.perform(post("/api/proposition")
                .header("authorization", addUser.getBearerToken())
                .content(objectMapper.writeValueAsString(al))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        AlertPropositionDto ap = objectMapper.readValue(r.getResponse().getContentAsString(), AlertPropositionDto.class);
        AlertProposition a = alertPrepositionRepo.findById(ap.getAlertId()).orElseThrow();


        MvcResult r2 = mockMvc.perform(get("/api/proposition/accept/" + a.getAlertId())
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        AlertPropositionDto ap2 = objectMapper.readValue(r2.getResponse().getContentAsString(), AlertPropositionDto.class);
        AlertProposition a2 = alertPrepositionRepo.findById(ap2.getAlertId()).orElseThrow();

        assertEquals(true, a2.isAccepted());

        alertPrepositionRepo.delete(a2);
    }

    @Test
    void should_add_point_to_alert_proposition() throws Exception {

        AddAlertPropositionDto al = new AddAlertPropositionDto(33.44,22.11,"bbbbbaaac", 1);
        MvcResult r = mockMvc.perform(post("/api/proposition")
                .header("authorization", addUser.getBearerToken())
                .content(objectMapper.writeValueAsString(al))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        AlertPropositionDto ap = objectMapper.readValue(r.getResponse().getContentAsString(), AlertPropositionDto.class);
        AlertProposition a = alertPrepositionRepo.findById(ap.getAlertId()).orElseThrow();


        MvcResult r2 = mockMvc.perform(get("/api/proposition/point/" + a.getAlertId())
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        AlertPropositionDto ap2 = objectMapper.readValue(r2.getResponse().getContentAsString(), AlertPropositionDto.class);
        AlertProposition a2 = alertPrepositionRepo.findById(ap2.getAlertId()).orElseThrow();

        assertEquals(1, a2.getPoints());

        alertPrepositionRepo.delete(a2);

    }

    @Test
    void should_get_danger_types() throws Exception {

        mockMvc.perform(get("/api/proposition/dangers")
                .header("authorization", addUser.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @AfterAll()
    void deleteUser() {
        authorizationService.deleteEmployee(user);
        alertProposition = alertPrepositionRepo.findById(Aid).orElseThrow();
        alertPrepositionRepo.delete(alertProposition);
        addUser.deleteUser();
    }
}
