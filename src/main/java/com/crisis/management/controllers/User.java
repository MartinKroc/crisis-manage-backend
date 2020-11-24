package com.crisis.management.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class User {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}