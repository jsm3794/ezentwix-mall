package com.ezentwix.teamcostco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginResultController {
    @GetMapping("/login/oauth_result")
    public String getMethodName() {
        return "login/oauth_result";
    }
}