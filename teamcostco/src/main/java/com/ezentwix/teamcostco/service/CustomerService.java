package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.dto.kakao_auth.KakaoUserDTO;
import com.ezentwix.teamcostco.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDTO getBySocialId(String social_id){
        return customerRepository.getBySocialId(social_id);
    }

    public Boolean insertCustomer(KakaoUserDTO kakaoUserDTO){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname(kakaoUserDTO.getProperties().getNickname());
        customerDTO.setSocial_id(kakaoUserDTO.getId());
        System.out.println(customerDTO);
        return customerRepository.insertCustomer(customerDTO);
    }
}
