package com.ezentwix.teamcostco.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ezentwix.teamcostco.service.AuthService;
import com.ezentwix.teamcostco.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;
    private final CustomerService customerService;

    @GetMapping("/login/check")
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
       
        String social_id = customerService.getSocialIdFromSession();
        if (social_id != null) {
            response.put("loggedIn", true);
            response.put("social_id", social_id);
        } else {
            response.put("loggedIn", false);
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public RedirectView logout() {
        authService.removeUserFromSession();
        return new RedirectView("/");
    }
    
}