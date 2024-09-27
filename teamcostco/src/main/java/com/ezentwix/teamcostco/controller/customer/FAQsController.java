package com.ezentwix.teamcostco.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.FAQsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FAQsController {
    private final FAQsService faqsService;

    @GetMapping("/customer/FAQs")
    public String showCustomerMain(Model model) {
        model.addAttribute("faqList", faqsService.getAllFAQList());
        faqsService.configureModel(model);
        return "index";
    }


}
