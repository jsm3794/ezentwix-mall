package com.ezentwix.teamcostco.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginRestController {

    @GetMapping("/login/check")
    public boolean checkLogin(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        return userId != null;
    }
}
