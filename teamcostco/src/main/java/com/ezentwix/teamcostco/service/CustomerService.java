package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoUserDTO;
import com.ezentwix.teamcostco.repository.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService implements PageMetadataProvider {
    private final CustomerRepository customerRepository;

    public String getUserIdFromSession() {
        // 현재 세션에서 HttpServletRequest 가져오기
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();

        // 세션에서 userId 가져오기
        String userId = (String) request.getSession().getAttribute("userId");

        return userId;
    }

    public CustomerDTO getCustomerFromSession(){
        String userId = getUserIdFromSession();

        return customerRepository.getBySocialId(userId);
    }

    public CustomerDTO getBySocialId(String social_id) {
        return customerRepository.getBySocialId(social_id);
    }

    public Boolean insertCustomer(KakaoUserDTO kakaoUserDTO) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname(kakaoUserDTO.getProperties().getNickname());
        customerDTO.setSocial_id(kakaoUserDTO.getId());
        System.out.println(customerDTO);
        return customerRepository.insertCustomer(customerDTO);
    }

    @Override
    public String getUri() {
        return "/customer/customer_info";
    }

    @Override
    public String getPageTitle() {
        return "사용자 정보";
    }
}
