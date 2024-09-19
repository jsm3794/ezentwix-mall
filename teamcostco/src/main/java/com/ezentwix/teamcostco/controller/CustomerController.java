package com.ezentwix.teamcostco.controller;

import com.ezentwix.teamcostco.service.DeliveryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final DeliveryService deliveryService;


    @GetMapping("/customer/delivery_management")
    public String deliveryManagement(Model model) {
        // 모델 구성
        deliveryService.configureModel(model);
        return "index";
    }

}
