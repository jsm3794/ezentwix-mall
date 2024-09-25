package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.repository.CustomerRepository;
import com.ezentwix.teamcostco.repository.EmployeeRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService  {
    private final HttpSession httpSession;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;


    public void storeUserInSession(String userId) {
        httpSession.setAttribute("userId", userId);
        httpSession.setAttribute("isLoggedIn", true);
    }

    public String getUserIdFromSession() {
        return (String)httpSession.getAttribute("userId");
    }

    public String getUserNickNameFromSession() {
        return (String)httpSession.getAttribute("user.nickname");
    }

    public void removeUserFromSession() {
        httpSession.removeAttribute("userId");
    }

    public CustomerDTO login(String id, String pw) {
        return employeeRepository.getByIdAndPw(id, pw);
    }
}
