package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.kakao_auth.KakaoUserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    public Boolean SignupWithKakao(KakaoUserDTO kakaoUserDTO) {
        
        return true;

    }
}
