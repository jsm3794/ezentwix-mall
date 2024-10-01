package com.ezentwix.teamcostco.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.CustomerService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class CustomerInfoPageController {
    private final CustomerService customerService;


    @GetMapping("/customer")
    public String redirectToCustomerInfo() {
        return "redirect:/customer/saleslist";
    }

    @GetMapping("/customer/customer_info")
    public String showCustomerMain(Model model) {
        customerService.configureModel(model);
        return "index";
    }
}
