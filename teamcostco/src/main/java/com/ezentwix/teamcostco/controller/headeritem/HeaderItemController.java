package com.ezentwix.teamcostco.controller.headeritem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.AboutService;
import com.ezentwix.teamcostco.service.ContactUsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HeaderItemController {

    private final AboutService aboutService;
    private final ContactUsService contactUsService;

    @GetMapping("/about-us")
    public String showAboutUs(Model model) {
        aboutService.configureModel(model);
        return "index";
    }

    @GetMapping("/contact-us")
    public String showContactUs(Model model) {
        contactUsService.configureModel(model);
        return "index";
    }

    

}
