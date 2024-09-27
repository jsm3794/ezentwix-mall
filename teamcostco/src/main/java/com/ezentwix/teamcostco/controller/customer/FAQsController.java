package com.ezentwix.teamcostco.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezentwix.teamcostco.service.FAQsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/FAQs")
public class FAQsController {
    private final FAQsService faqsService;

    @GetMapping("")
    public String showFAQs(Model model) {
        faqsService.configureModel(model);
        return "index";
    }

    @GetMapping("/detail/{id}")
    public String showFAQDetail(@RequestParam String param) {
        return "index";
    }
    
}
