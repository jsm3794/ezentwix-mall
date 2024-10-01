package com.ezentwix.teamcostco.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginPageController {
    private final AuthService loginService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }
}
