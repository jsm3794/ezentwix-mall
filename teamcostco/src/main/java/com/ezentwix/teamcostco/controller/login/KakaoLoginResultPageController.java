package com.ezentwix.teamcostco.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginResultPageController {
    @GetMapping("/login/oauth_result")
    public String getMethodName() {
        return "login/oauth_result";
    }
}