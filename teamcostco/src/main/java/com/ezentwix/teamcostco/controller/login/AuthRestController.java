package com.ezentwix.teamcostco.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ezentwix.teamcostco.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @GetMapping("/login/check")
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String userId = (String) session.getAttribute("userId");
        
        if (userId != null) {
            response.put("loggedIn", true);
            response.put("customerId", userId); // 실제 구현에서는 고객 ID를 적절히 가져와야 합니다.
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