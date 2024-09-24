package com.ezentwix.teamcostco.dto.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerDTO {
    private String customer_id; // 고객 고유 ID
    private String nickname; // 이름
    private String email; // 이메일
    private String phone_number; // 전화번호
    private LocalDate date_of_birth; // 생일
    private Character gender; // 성별 (M/F)
    private String status; // 상태 (ACTIVE/INACTIVE 등)
    private LocalDateTime created_at; // 가입일
    private LocalDateTime updated_at; // 마지막 수정일
    private String default_address_id;

    // 소셜 로그인 관련 필드
    private String social_provider; // 소셜 로그인 제공자 (예: KAKAO, GOOGLE)
    private String social_id; // 소셜 로그인 ID (유일)
}