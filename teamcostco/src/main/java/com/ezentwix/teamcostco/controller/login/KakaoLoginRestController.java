package com.ezentwix.teamcostco.controller.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoTokenResponseDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoUserDTO;
import com.ezentwix.teamcostco.service.CustomerService;
import com.ezentwix.teamcostco.service.KakaoAuthService;
import com.ezentwix.teamcostco.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginRestController {
    private final KakaoAuthService kakaoAuthService;
    private final LoginService loginService;
    private final CustomerService customerService;

    @GetMapping("/login/oauth")
    public RedirectView getKakaoTokenResponse(@RequestParam String code) {
        try {
            // 카카오 토큰 요청
            KakaoTokenResponseDTO kakaoTokenResponseDTO = kakaoAuthService.requestToken(code);
            // 카카오 사용자 정보 요청
            KakaoUserDTO kakaoUserDTO = kakaoAuthService.getUserInfo(kakaoTokenResponseDTO.getAccess_token());
            // 사용자 정보 조회
            CustomerDTO customerDTO = customerService.getBySocialId(kakaoUserDTO.getId());

            if (customerDTO != null) {
                // 사용자 정보가 존재하면
                // 세션에 카카오 사용자 정보 추가
                loginService.storeUserInSession(kakaoUserDTO.getId());
                return new RedirectView("/login/oauth_result");
            } else {
                // 사용자 정보가 없으면
                // 신규 고객 정보 추가
                if (customerService.insertCustomer(kakaoUserDTO)) {
                    // 세션에 카카오 사용자 정보 추가
                    loginService.storeUserInSession(kakaoUserDTO.getId());
                    return new RedirectView("/login/oauth_result");
                }
            }
        } catch (Exception e) {
            log.error("Error during Kakao login", e);
        }
        return new RedirectView("/login/oauth_result");
    }
}
