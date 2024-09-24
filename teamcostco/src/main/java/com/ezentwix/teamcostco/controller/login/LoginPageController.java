package com.ezentwix.teamcostco.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginPageController {

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "/login";
    }
}
