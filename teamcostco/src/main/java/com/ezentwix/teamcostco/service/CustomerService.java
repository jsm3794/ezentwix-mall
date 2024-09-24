package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ezentwix.teamcostco.PageMetadataProvider;
=======

>>>>>>> origin/wishlist
import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoUserDTO;
import com.ezentwix.teamcostco.repository.CustomerRepository;

<<<<<<< HEAD
import jakarta.servlet.http.HttpServletRequest;
=======
>>>>>>> origin/wishlist
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
<<<<<<< HEAD
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

    public CustomerDTO getBySocialId(String social_id) {
        return customerRepository.getBySocialId(social_id);
    }

    public Boolean insertCustomer(KakaoUserDTO kakaoUserDTO) {
=======
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDTO getBySocialId(String social_id){
        return customerRepository.getBySocialId(social_id);
    }

    public Boolean insertCustomer(KakaoUserDTO kakaoUserDTO){
>>>>>>> origin/wishlist
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname(kakaoUserDTO.getProperties().getNickname());
        customerDTO.setSocial_id(kakaoUserDTO.getId());
        System.out.println(customerDTO);
        return customerRepository.insertCustomer(customerDTO);
    }
<<<<<<< HEAD

    @Override
    public String getUri() {
        return "/customer/customer_info";
    }

    @Override
    public String getPageTitle() {
        return "사용자 정보";
    }
=======
>>>>>>> origin/wishlist
}
