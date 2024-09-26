package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.repository.CustomerRepository;
import com.ezentwix.teamcostco.repository.EmployeeRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService  {
    private final HttpSession httpSession;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;


    public void storeCusomterInSession(String socialId) {
        CustomerDTO customerDTO = customerRepository.getBySocialId(socialId);
        httpSession.setAttribute("customerId", customerDTO.getCustomer_id());
        httpSession.setAttribute("socialId", customerDTO.getSocial_id());
        httpSession.setAttribute("isLoggedIn", true);
        httpSession.setAttribute("nickname", customerDTO.getNickname());
    }

    public String getSocialIdFromSession() {
        return (String)httpSession.getAttribute("socialId");
    }

    public String getNickNameFromSession() {
        return (String)httpSession.getAttribute("nickname");
    }

    public void removeUserFromSession() {
        httpSession.removeAttribute("customerId");
        httpSession.removeAttribute("socialId");
        httpSession.removeAttribute("isLoggedIn");
        httpSession.removeAttribute("nickname");
    }

    public CustomerDTO login(String id, String pw) {
        return employeeRepository.getByIdAndPw(id, pw);
    }
}
