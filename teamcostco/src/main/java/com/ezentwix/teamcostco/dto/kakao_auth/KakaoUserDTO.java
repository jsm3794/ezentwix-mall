package com.ezentwix.teamcostco.dto.kakao_auth;

import java.time.LocalDateTime;

import com.google.type.DateTime;

import lombok.Data;

@Data
public class KakaoUserDTO {
    private String id; // 회원번호
    private Boolean has_signed_up; // 연결 완료 여부
    private LocalDateTime connected_at; // 서비스에 연결 완료된 시각
    private LocalDateTime synched_at; // 카카오싱크 간편가입 로그인 시각
    private Properties properties; // 사용자 프로퍼티
    private KakaoAccount kakao_account; // 카카오계정 정보
    private Partner for_partner; // 추가 정보 (uuid 등)


    @Data
    public static class KakaoAccount {
        private Boolean profile_needs_agreement; // 프로필 동의 필요 여부
        private Boolean profile_nickname_needs_agreement; // 닉네임 동의 필요 여부
        private Boolean profile_image_needs_agreement; // 프로필 이미지 동의 필요 여부
        private Boolean name_needs_agreement; // 카카오계정 이름 동의 필요 여부
        private String name; // 카카오계정 이름
        private Boolean email_needs_agreement; // 이메일 동의 필요 여부
        private Boolean is_email_valid; // 이메일 유효 여부
        private Boolean is_email_verified; // 이메일 인증 여부
        private String email; // 카카오계정 이메일
        private Boolean age_range_needs_agreement; // 연령대 동의 필요 여부
        private String age_range; // 연령대
        private Boolean birthyear_needs_agreement; // 출생 연도 동의 필요 여부
        private String birthyear; // 출생 연도
        private Boolean birthday_needs_agreement; // 생일 동의 필요 여부
        private String birthday; // 생일
        private String birthday_type; // 생일 타입 (양력/음력)
        private Boolean gender_needs_agreement; // 성별 동의 필요 여부
        private String gender; // 성별
        private Boolean phone_number_needs_agreement; // 전화번호 동의 필요 여부
        private String phone_number; // 전화번호
        private Boolean ci_needs_agreement; // CI 동의 필요 여부
        private String ci; // CI
        private LocalDateTime ci_authenticated_at; // CI 발급 시각
        private Profile profile; // 프로필 정보

        @Data
        public static class Profile {
            private String nickname; // 닉네임
            private String thumbnail_image_url; // 프로필 미리보기 이미지 URL
            private String profile_image_url; // 프로필 사진 URL
            private Boolean is_default_image; // 기본 프로필 사진 여부
            private Boolean is_default_nickname; // 기본 닉네임 여부
        }
    }

    @Data
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    public static class Partner {
        private String uuid;
    }
}
