package com.ezentwix.teamcostco.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import com.ezentwix.teamcostco.dto.kakao_auth.KakaoTokenRequestDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoTokenResponseDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
@ApplicationScope
public class KakaoAuthService {

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Value("${kakao.client_secret:}")
    private String clientSecret;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public KakaoTokenResponseDTO requestToken(String authorizationCode) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        KakaoTokenRequestDTO tokenRequest = new KakaoTokenRequestDTO();
        tokenRequest.setGrant_type("authorization_code");
        tokenRequest.setClient_id(clientId);
        tokenRequest.setRedirect_uri(redirectUri);
        tokenRequest.setCode(authorizationCode);
        if (clientSecret != null && !clientSecret.isEmpty()) {
            tokenRequest.setClient_secret(clientSecret);
        }

        String formData = getFormData(tokenRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), KakaoTokenResponseDTO.class);
    }

    private String getFormData(KakaoTokenRequestDTO params) throws UnsupportedEncodingException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", params.getGrant_type());
        paramMap.put("client_id", params.getClient_id());
        paramMap.put("redirect_uri", params.getRedirect_uri());
        paramMap.put("code", params.getCode());
        if (params.getClient_secret() != null && !params.getClient_secret().isEmpty()) {
            paramMap.put("client_secret", params.getClient_secret());
        }

        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()) + "=" +
                    URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
        }
        return sj.toString();
    }

    public KakaoUserDTO getUserInfo(String accessToken) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response;

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(USER_INFO_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println(response.body());
                return objectMapper.readValue(response.body(), KakaoUserDTO.class);
            } else {
                throw new RuntimeException("Failed to get user info: " + response.statusCode() + " " + response.body());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get user info", e);
        }
    }
}
